package com.huan.netty.protostuff;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
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
						ch.pipeline().addLast(new ProtostuffDecoder());
						ch.pipeline().addLast(new ProtoStuffServerHandler());
					}
				});
		ChannelFuture future = bootstrap.bind(9091).sync();
		log.info("server start in port:[{}]", 9091);
		future.channel().closeFuture().sync();
		boss.shutdownGracefully();
		worker.shutdownGracefully();
	}
}
