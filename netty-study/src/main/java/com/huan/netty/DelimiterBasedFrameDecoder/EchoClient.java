package com.huan.netty.DelimiterBasedFrameDecoder;

import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.Bootstrap;
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
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @user 付焕
 * @date 2017/11/10 - 15:29
 */
@Slf4j
public class EchoClient {

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)//
				.channel(NioSocketChannel.class)//
				.option(ChannelOption.TCP_NODELAY, true)//
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ByteBuf delimiter = Unpooled.copiedBuffer("^^".getBytes(StandardCharsets.UTF_8));
						ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(new EchoClientHandler());
					}
				});
		ChannelFuture future = bootstrap.connect("127.0.0.1", 9090).sync();
		log.info("client connect server.");
		future.channel().closeFuture().sync();
		group.shutdownGracefully();
	}

	@Slf4j
	static class EchoClientHandler extends ChannelInboundHandlerAdapter {
		private int counter;
		private static final String ECHO_REQ = "hello server.服务器好啊.^^";

		/**
		 * 客户端和服务器端TCP链路建立成功后，此方法被调用
		 */
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			for (int i = 0; i < 10; i++) {
				ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes(StandardCharsets.UTF_8)));
			}
		}

		/**
		 * 接收到服务器端数据时调用
		 */
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			log.info("this is :{} revice server msg:{}", ++counter, msg);
		}

		/**
		 * 发生异常时调用
		 */
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			log.error("client error:", cause);
			ctx.close();
		}
	}
}
