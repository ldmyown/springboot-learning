package com.telangel.handler;

import com.telangel.service.HttpFileServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.Values.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @version 1.0.0
 * @项目名称： springboot-netty
 * @类名称： HttpFileServerHandler
 * @类描述：
 * @author： lid
 * @date： 2019/3/13 16:02
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpFileServerHandler.class);

    private static final HttpDataFactory FACTORY = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

    private String url;

    private HttpPostRequestDecoder decoder;

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    /**
     * 连接上服务器
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("【连接服务器】====>" + ctx.channel().id());
    }

    /**
     * 断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("【关闭连接】====>" + ctx.channel().id());
    }

    /**
     * 连接异常   需要关闭相关资源
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("【数据文件通过过程中出现异常】======>" + cause.toString(), cause);
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR,  "数据文件通过过程中出现异常：" + cause.getMessage());
        }
        ctx.close();
        ctx.channel().close();
    }

    /**
     * 活跃的通道  也可以当作用户连接上客户端进行使用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("【连接成功】=====>" + ctx.channel());
    }

    /**
     * 不活跃的通道  就说明用户失去连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }

    /**
     * 只要完成 flush
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("【读取数据完成】=====>" + ctx.channel());
        ctx.flush();
    }

    /**
     * 这里是保持服务器与客户端长连接  进行心跳检测 避免连接断开
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            PingWebSocketFrame ping = new PingWebSocketFrame();
            switch (stateEvent.state()) {
                //读空闲（服务器端）
                case READER_IDLE:
                    LOGGER.info("【" + ctx.channel().remoteAddress() + "】读空闲（服务器端）");
                    ctx.writeAndFlush(ping);
                    break;
                //写空闲（客户端）
                case WRITER_IDLE:
                    LOGGER.info("【" + ctx.channel().remoteAddress() + "】写空闲（客户端）");
                    ctx.writeAndFlush(ping);
                    break;
                case ALL_IDLE:
                    LOGGER.info("【" + ctx.channel().remoteAddress() + "】读写空闲");
                    break;
                default:
            }
        }
    }

    /**
     * 收发消息处理
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        doHandlerHttpRequest(ctx, msg);
    }


    /**
     * websocket消息处理
     *
     * @param ctx
     * @param msg
     */
    private void doHandlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        //判断msg 是哪一种类型  分别做出不同的反应
        if (msg instanceof CloseWebSocketFrame) {
            LOGGER.info("【关闭】");
//            handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg);
            return;
        }
        if (msg instanceof PingWebSocketFrame) {
            LOGGER.info("【ping】");
            PongWebSocketFrame pong = new PongWebSocketFrame(msg.content().retain());
            ctx.channel().writeAndFlush(pong);
            return;
        }
        if (msg instanceof PongWebSocketFrame) {
            LOGGER.info("【pong】");
            PingWebSocketFrame ping = new PingWebSocketFrame(msg.content().retain());
            ctx.channel().writeAndFlush(ping);
            return;
        }
        if (!(msg instanceof TextWebSocketFrame)) {
            LOGGER.info("【不支持二进制】");
            throw new UnsupportedOperationException("不支持二进制");
        }

    }

    /**
     * wetsocket第一次连接握手
     *
     * @param ctx
     * @param msg
     */
    private void doHandlerHttpRequest(ChannelHandlerContext ctx, FullHttpRequest msg) throws IOException {
        // http 解码失败
        if (!msg.getDecoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }

        // get请求是下载文件
        if (msg.getMethod() == HttpMethod.GET) {
            downloadOrListFile(ctx, msg);
        }
        // post 请求是上传文件
        else if (msg.getMethod() == HttpMethod.POST) {
            if (decoder != null) {
                decoder.cleanFiles();
                decoder = null;
            }
            decoder = new HttpPostRequestDecoder(FACTORY, msg);
            if (decoder != null && msg instanceof HttpContent) {
                HttpContent chunk = msg;

                decoder.offer(chunk);

                readHttpDataChunkByChunk();

                if (chunk instanceof LastHttpContent) {
                    reset();
                    return;
                }
            }
        } else {
            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
        }
    }

    /**
     * 如果是文件夹则显示列表，如果是文件则下载文件
     *
     * @param ctx
     * @param msg
     */
    private void downloadOrListFile(ChannelHandlerContext ctx, HttpRequest msg) {
        String uri = msg.getUri();
        if (StringUtils.isEmpty(uri)) {
            uri = "/";
        }
        if ("/".equalsIgnoreCase(uri.trim())) {
            uri = url;
        }
        if (!uri.startsWith(url)) {
            uri = url + uri;
        }
        final String path = sanitizeUri(uri);
        if (path == null) {
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }

        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }

        if (file.isDirectory()) {
            senfListing(ctx, file);
        } else {
            solveFile(ctx, msg, file);
        }
    }

    /**
     * 通过chunk读取request，获取chunk数据
     *
     * @throws IOException
     */
    private void readHttpDataChunkByChunk() throws IOException {
        try {
            while (decoder.hasNext()) {

                InterfaceHttpData data = decoder.next();
                if (data != null) {
                    try {
                        writeHttpData(data);
                    } finally {
                        data.release();
                    }
                }
            }
        } catch (HttpPostRequestDecoder.EndOfDataDecoderException e1) {
            LOGGER.error("end chunk failed", e1);
        }
    }

    private void writeHttpData(InterfaceHttpData data) throws IOException {
        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
            FileUpload fileUpload = (FileUpload) data;
            if (fileUpload.isCompleted()) {

                StringBuffer fileNameBuf = new StringBuffer();
                fileNameBuf.append(DiskFileUpload.baseDirectory)
                        .append("aa");

                fileUpload.renameTo(new File(fileNameBuf.toString()));
            }
        }
    }

    /**
     * 对文件进行处理
     *
     * @param ctx
     * @param msg
     * @param file
     */
    private void solveFile(ChannelHandlerContext ctx, HttpRequest msg, File file) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            Long fileLength = randomAccessFile.length();
            HttpResponse httpResponse = new DefaultHttpResponse(HTTP_1_1, OK);
            setContentLength(httpResponse, fileLength);
            setContentTypeHeader(httpResponse, file);

            if (isKeepAlive(msg)) {
                httpResponse.headers().set(CONNECTION, KEEP_ALIVE);
            }
            ctx.writeAndFlush(httpResponse);
            ChannelFuture sendFileFuture = ctx.write(
                    new ChunkedFile(randomAccessFile, 0, fileLength, 8192), ctx.newProgressivePromise());

            sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
                @Override
                public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
                    if (total < 0) {
                        System.err.println("progress:" + progress);
                    } else {
                        System.err.println("progress:" + progress + "/" + total);
                    }
                }
                @Override
                public void operationComplete(ChannelProgressiveFuture future) {
                    System.err.println("complete");
                }
            });

            ChannelFuture lastChannelFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!isKeepAlive(msg)) {
                lastChannelFuture.addListener(ChannelFutureListener.CLOSE);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        } catch (IOException e) {
            e.printStackTrace();
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
        }
    }

    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    public String sanitizeUri(String uri) {
        try {
            uri = URLEncoder.encode(uri, "UTF-8");
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (Exception e) {
            try {
                uri = URLEncoder.encode(uri, "ISO-8859-1");
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (Exception ew) {
                ew.printStackTrace();
            }
        }
        if (!uri.startsWith(url)) {
            return null;
        }
        uri = uri.replace('/', File.separatorChar);
        if (uri.contains(File.separator + '.') || uri.startsWith(".") || uri.endsWith(".") || INSECURE_URI.matcher(uri).matches()) {
            return null;
        }
        return uri;

    }

    /**
     * 将目录列出来
     *
     * @param channelHandlerContext
     * @param dir
     */
    private void senfListing(ChannelHandlerContext channelHandlerContext, File dir) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
        response.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");
        StringBuilder builder = new StringBuilder();
        String dirPath = dir.getPath();
        builder.append("<!DOCTYPE html> \r\n");
        builder.append("<html><head><title>");
        builder.append(dirPath);
        builder.append("目录:");
        builder.append("</title></head><body>\r\n");
        builder.append("<h3>");
        builder.append("文件服务器：");
        builder.append("</h3>\r\n");
        builder.append("<ul>");
        url = url.replace("/", File.separator);
        builder.append("<li>链接：<a href=\"" + dir.getParent().replace(url, "") + File.separator + "\">..</a></li>\r\n");
        for (File f : dir.listFiles()) {
            if (f.isHidden() || !f.canRead()) {
                continue;
            }
            // 过滤进度文件
            if("conf".equals(f.getName())) {
                continue;
            }
            String fname = f.getName();
//            if (!ALLOWED_FILE_NAME.matcher(fname).matches()) {
//                continue;
//            }
            builder.append("<li>链接：<a href=\" ");
            builder.append(dirPath.replace(url, "") + File.separator + fname);
            builder.append("\" >");
            builder.append(fname);
            builder.append("</a></li>\r\n");
        }
        builder.append("</ul></body></html>\r\n");

        ByteBuf byteBuf = Unpooled.copiedBuffer(builder, CharsetUtil.UTF_8);
        response.content().writeBytes(byteBuf);
        byteBuf.release();
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }


    private void sendRedirect(ChannelHandlerContext channelHandlerContext, String newUri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FOUND);
        response.headers().set(LOCATION, newUri);
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private void sendError(ChannelHandlerContext channelHandlerContext, HttpResponseStatus status) {
        sendError(channelHandlerContext, status, "");
    }

    private void sendError(ChannelHandlerContext channelHandlerContext, HttpResponseStatus status, String msg) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n" + msg,
                CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }


    private void setContentTypeHeader(HttpResponse httpResponse, File file) {
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        httpResponse.headers().set(CONTENT_TYPE, mimetypesFileTypeMap.getContentType(file.getPath()));
    }

    //销毁decoder释放所有的资源
    private void reset() {
        decoder.destroy();
        decoder = null;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        doHandlerHttpRequest(ctx, msg);
    }
}
