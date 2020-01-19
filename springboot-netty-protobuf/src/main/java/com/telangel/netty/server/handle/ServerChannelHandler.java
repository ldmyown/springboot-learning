package com.telangel.netty.server.handle;

import com.telangel.netty.server.NettyServer;
import com.telangel.protobuf.Kncp;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author:lid
 * @date: 2018-11-28 15:49
 **/
@Component
@ChannelHandler.Sharable
@Slf4j
public class ServerChannelHandler extends SimpleChannelInboundHandler<Kncp.ProtoMessage> {

    /**
     * 拿到传过来的msg数据，开始处理
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Kncp.ProtoMessage msg) throws Exception {
        switch (msg.getPackType()) {
            case LOGIN:
                log.info("接收到一个登录类型的pack:[{}]");
                break;
            case CREATE_TASK:
                log.info("接收到一个创建任务类型的pack:[{}]");
                break;
            case DELETE_TASK:
                log.info("接收到一个删除任务类型的pack:[{}]");
                break;
            default:
                log.error("接收到一个未知类型的pack:[{}]", msg.getPackType());
                break;
        }
    }

    /**
     * 活跃的、有效的通道
     * 第一次连接成功后进入的方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("客户端：" + getRemoteAddress(ctx) + " 连接成功");
//        UserInfo.UserMsg userMsg = UserInfo.UserMsg.newBuilder().setId(1).setAge(18).setName("xuwujing").setState(0)
//                .build();
//        ctx.writeAndFlush(userMsg);
        //往channel map中添加channel信息
        NettyServer.map.put(getIPString(ctx), ctx.channel());
    }

    /**
     * 不活动的通道
     * 连接丢失后执行的方法（client端可据此实现断线重连）
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //删除Channel Map中的失效Client
        NettyServer.map.remove(getIPString(ctx));
        ctx.close();
    }

    /**
     * 异常处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        // 发生异常，关闭连接
        log.error("引擎 {} 的通道发生异常，即将断开连接", getRemoteAddress(ctx));
        // 再次建议close
        ctx.close();
    }

    /**
     * 心跳机制，超时处理
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String socketString = ctx.channel().remoteAddress().toString();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.debug("客户端: " + socketString + " READER_IDLE 读超时");
                ctx.disconnect();//断开
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.debug("客户端: " + socketString + " WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.debug("客户端: " + socketString + " ALL_IDLE 总超时");
                ctx.disconnect();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 获取client对象：ip+port
     *
     * @param ctx
     * @return
     */
    public String getRemoteAddress(ChannelHandlerContext ctx) {
        String socketString = "";
        socketString = ctx.channel().remoteAddress().toString();
        return socketString;
    }

    /**
     * 获取client的ip
     *
     * @param ctx
     * @return
     */
    public String getIPString(ChannelHandlerContext ctx) {
        String ipString = "";
        String socketString = ctx.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1, colonAt);
        return ipString;
    }

}