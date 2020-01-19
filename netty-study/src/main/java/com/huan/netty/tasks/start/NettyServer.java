package com.huan.netty.tasks.start;

import java.util.concurrent.TimeUnit;

import com.huan.netty.tasks.decoder.PackDecoder;
import com.huan.netty.tasks.encoder.PackEncoder;
import com.huan.netty.tasks.handler.PackServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {
	public static void main(String[] args) throws Exception {
		EventLoopGroup boss = new NioEventLoopGroup(1);
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, worker)//
				.channel(NioServerSocketChannel.class)// 对应的是ServerSocketChannel类
				.option(ChannelOption.SO_BACKLOG, 128)//
				.handler(new LoggingHandler(LogLevel.TRACE))//
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(10240, 0, 4, 0, 4));
						ch.pipeline().addLast(new PackDecoder());
						ch.pipeline().addLast(new LengthFieldPrepender(4));
						ch.pipeline().addLast(new PackEncoder());
						ch.pipeline().addLast(new IdleStateHandler(3, 3, 3, TimeUnit.SECONDS));
						ch.pipeline().addLast(new PackServerHandler());
					}
				});
		ChannelFuture future = bootstrap.bind(9091).sync();
		log.info("server start in port:[{}]", 9091);
		future.channel().closeFuture().sync();
		boss.shutdownGracefully();
		worker.shutdownGracefully();
	}
}
