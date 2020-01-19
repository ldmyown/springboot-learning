package com.telangel.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * netty 客户端
 *
 * @author lid
 * @date 2020/1/16 10:47
 */
@Component
@Slf4j
public class NettyClient {

    @Value(("${netty.tcp.server.host}"))
    String HOST;
    @Value("${netty.tcp.server.port}")
    int PORT;

    @Autowired
    ClientChannelInitializer clientChannelInitializer;

    /**
     * 与服务端建立连接后得到的通道对象
     */
    private Channel channel;

    /**
     * 初始化 `Bootstrap` 客户端引导程序
     *
     * @return
     */
    private final Bootstrap getBootstrap() {
        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                //通道连接者
                .channel(NioSocketChannel.class)
                //通道处理者
                .handler(clientChannelInitializer)
                //心跳报活
                .option(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }

    /**
     * 建立连接，获取连接通道对象
     *
     * @return
     */
    public void connect() {
        ChannelFuture channelFuture = getBootstrap().connect(HOST, PORT).syncUninterruptibly();
        if (channelFuture != null && channelFuture.isSuccess()) {
            channel = channelFuture.channel();
            log.info("connect tcp server host = {}, port = {} success", HOST, PORT);
        } else {
            log.error("connect tcp server host = {}, port = {} fail", HOST, PORT);
        }
    }

    /**
     * 向服务器发送消息
     *
     * @param msg
     * @throws Exception
     */
    public void sendMsg(Object msg) throws Exception {
        if (channel != null) {
            channel.writeAndFlush(msg).sync();
        } else {
            log.warn("消息发送失败,连接尚未建立!");
        }
    }

}
