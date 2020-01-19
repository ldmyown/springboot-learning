package com.huan.netty.DelimiterBasedFrameDecoder;

import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @user 付焕
 * @date 2017/11/10 - 15:10 netty server
 */
@Slf4j
public class EchoServer {

	public static void main(String[] args) throws InterruptedException {
		/** 此线程组用于服务单接收客户端的连接 */
		EventLoopGroup boss = new NioEventLoopGroup(1);
		/** 此线程组用于处理SocketChannel的读写操作 */
		EventLoopGroup worker = new NioEventLoopGroup();
		/** netty启动服务端的辅助类 */
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, worker)//
				.channel(NioServerSocketChannel.class)// 对应的是ServerSocketChannel类
				.option(ChannelOption.SO_BACKLOG, 128)//
				.handler(new LoggingHandler(LogLevel.TRACE))//
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ByteBuf delimiter = Unpooled.copiedBuffer("^^".getBytes(StandardCharsets.UTF_8));
						// 表示客户端的数据中只要出现了^^就表示是一个完整的包，maxFrameLength这个表示如果在这个多个字节中还没有出现则表示数据有异常情况，抛出异常
						ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
						// 将接收到的数据转换成String的类型
						ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
						// 接收到的数据由自己的EchoServerHandler类进行处理
						ch.pipeline().addLast(new EchoServerHandler());
					}
				});
		// 绑定端口，同步等待成功
		ChannelFuture future = bootstrap.bind(9090).sync();
		log.info("server start in port:[{}]", 9090);
		// 等待服务端链路关闭后,main线程退出
		future.channel().closeFuture().sync();
		// 关闭线程池资源
		boss.shutdownGracefully();
		worker.shutdownGracefully();
	}

	@Slf4j
	static class EchoServerHandler extends ChannelInboundHandlerAdapter {
		private int counter = 0;

		/**
		 * 接收到数据的时候调用
		 */
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			String body = (String) msg;
			log.info("this is: {} times receive cliemt msg: {}", ++counter, body);
			body += "^^";
			// 此处将数据写会到客户端，如果使用的是ctx.write方法这个只是将数据写入到缓冲区，需要再次调用ctx.flush方法
			ctx.writeAndFlush(Unpooled.copiedBuffer(body.getBytes(StandardCharsets.UTF_8)));
		}

		/** 当发生了异常时，次方法调用 */
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			log.error("error:", cause);
			ctx.close();
		}
	}

}
