package com.telangel.init;

import com.telangel.handler.HttpFileServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author lid
 */
public class FileServerInitializer extends ChannelInitializer<SocketChannel> {

    private String url;

    public FileServerInitializer(String url) {
        this.url = url;
    }

    @Override
    public void initChannel(SocketChannel socketChannel) {
        // HttpServerCodec：将请求和应答消息解码为HTTP消息
        // socketChannel.pipeline().addLast("http-codec",new HttpServerCodec());
        socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
        socketChannel.pipeline().addLast("http-encoder", new HttpResponseEncoder());

        // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
        socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
        // ChunkedWriteHandler：向客户端发送HTML5文件
        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        // 进行设置心跳检测
        socketChannel.pipeline().addLast(new IdleStateHandler(60, 30, 60 * 30, TimeUnit.SECONDS));
        // 配置通道处理  来进行业务处理
        socketChannel.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
    }
}  