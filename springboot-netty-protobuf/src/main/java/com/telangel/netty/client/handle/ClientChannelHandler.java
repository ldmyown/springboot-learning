package com.telangel.netty.client.handle;

import com.telangel.protobuf.Kncp;
import com.telangel.protobuf.Kncp.ProtoMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.telangel.protobuf.Kncp.PackType.CREATE_TASK;
import static com.telangel.protobuf.Kncp.PackType.LOGIN;

/**
 * @author lid
 * @date: 2018-12-28 14:06
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class ClientChannelHandler extends SimpleChannelInboundHandler<ProtoMessage> {

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private AtomicInteger atomicInteger = new AtomicInteger(1);

    /**
     * 循环次数
     */
    private int fcount = 1;

    /**
     * 从服务器接收到的msg
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoMessage msg) throws Exception {
//        // 如果不是protobuf类型的数据
//        if (!(msg instanceof UserInfo.UserMsg)) {
//            System.out.println("未知数据!" + msg);
//            return;
//        }
//        try {
//            // 得到protobuf的数据
//            UserInfo.UserMsg userMsg = (UserInfo.UserMsg) msg;
//            // 进行相应的业务处理。。。
//            // 这里就从简了，只是打印而已
//            System.out.println(
//                    "客户端接受到的用户信息。编号:" + userMsg.getId() + ",姓名:" + userMsg.getName() + ",年龄:" + userMsg.getAge());
//
//            // 这里返回一个已经接受到数据的状态
//            UserInfo.UserMsg.Builder userState = UserInfo.UserMsg.newBuilder().setState(1);
//            ctx.writeAndFlush(userState);
//            System.out.println("成功发送给服务端!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
    }

    /**
     * 建立连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端：{} 连接成功，连接时间：{}", getRemoteAddress(ctx), LocalDateTime.now());
        ctx.fireChannelActive();
        executor.scheduleAtFixedRate(() -> {
            // 产生的pack类型
            int packType = new Random().nextInt(3);
            switch (Kncp.PackType.valueOf(packType)) {
                case LOGIN:
                    Kncp.KNCP_REQUEST_LOGIN loginPack = Kncp.KNCP_REQUEST_LOGIN.newBuilder().setSzCertCn("张三[" + atomicInteger.getAndIncrement() + "]").setSzEMail("123456").build();
                    ctx.writeAndFlush(ProtoMessage.newBuilder().setPackType(Kncp.PackType.LOGIN).setKncpRequestLogin(loginPack).build());
                    break;
                case CREATE_TASK:
                    Kncp.KNCP_RESPONSE createTaskPack = Kncp.KNCP_RESPONSE.newBuilder().setNErrCode(1).build();
                    ctx.writeAndFlush(ProtoMessage.newBuilder().setPackType(Kncp.PackType.CREATE_TASK).setKncpResponse(createTaskPack).build());
                    break;
                case DELETE_TASK:
                    Kncp.KNCP_RESPONSE_CHALLENGE kncpResponseChallenge = Kncp.KNCP_RESPONSE_CHALLENGE.newBuilder().setNErrCode(1).setNCertInterfaceType(2).build();
                    ctx.writeAndFlush(ProtoMessage.newBuilder().setPackType(Kncp.PackType.DELETE_TASK).setKncpResponseChallenge(kncpResponseChallenge).build());
                    break;
                default:
                    log.error("产生一个未知的包类型:[{}]", packType);
                    break;
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * 关闭连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端：{} 关闭连接，关闭时间：{}", getRemoteAddress(ctx), LocalDateTime.now());
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
     * 心跳请求处理 每4秒发送一次心跳请求;
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        log.debug("循环请求的时间：{}, 次数：{}", LocalDateTime.now(), fcount);
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
//            if (IdleState.WRITER_IDLE.equals(event.state())) { // 如果写通道处于空闲状态,就发送心跳命令
//                UserInfo.UserMsg.Builder userState = UserInfo.UserMsg.newBuilder().setState(2);
//                ctx.channel().writeAndFlush(userState);
//                fcount++;
//            }
        }
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