package com.huan.netty.time.server;

import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @user 付焕
 * @date 2017/11/10 - 9:33 服务端
 */
@Slf4j
public class TimeServer {

	public static void main(String[] args) {
		/** netty启动服务端的辅助类 */
		ServerBootstrap bootstrap = new ServerBootstrap();
		/** 此线程组用于服务单接收客户端的连接 */
		EventLoopGroup boss = new NioEventLoopGroup();
		/** 此线程组用于处理SocketChannel的读写操作 */
		EventLoopGroup work = new NioEventLoopGroup();
		bootstrap.group(boss, work)//
				.channel(NioServerSocketChannel.class)// 对应的是ServerSocketChannel类
				.option(ChannelOption.SO_BACKLOG, 128)//
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
						socketChannel.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
						socketChannel.pipeline().addLast(new TimeServerHandler());
					}
				});
		try {
			// 绑定端口，同步等待成功
			ChannelFuture channelFuture = bootstrap.bind(9090).sync();
			log.info("netty服务端启动成功,监听:[{}]端口.", 9090);
			// 等待服务端链路关闭后,main线程退出
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			log.error("netty服务端出现异常.", e);
		} finally {
			// 关闭线程池资源
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}

	}

}
