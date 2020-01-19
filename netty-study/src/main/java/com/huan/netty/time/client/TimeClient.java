package com.huan.netty.time.client;

import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @user 付焕
 * @date 2017/11/10 - 10:33 客户端
 */
@Slf4j
public class TimeClient {
	public static void main(String[] args) {
		// 配置客户端线程组
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)//
				.channel(NioSocketChannel.class)//
				.option(ChannelOption.TCP_NODELAY, true)//
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
						ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
						ch.pipeline().addLast(new TimeClientHandler());
					}
				});
		try {
			// 发起异步连接操作,然后调用sync等待同步连接成功
			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9090).sync();
			// 等待客户端链路关闭
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			log.error("netty 客户端连接服务端出现异常.", e);
		} finally {
			group.shutdownGracefully();
		}
	}
}
