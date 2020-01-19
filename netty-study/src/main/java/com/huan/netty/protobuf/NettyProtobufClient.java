package com.huan.netty.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * netty protobuf client
 *
 * @author huan.fu
 * @date 2019/2/15 - 11:54
 */
@Slf4j
public class NettyProtobufClient {

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) {
						ch.pipeline()
								.addLast(new ProtobufVarint32FrameDecoder())
								.addLast(new ProtobufDecoder(TaskProtobufWrapper.TaskProtocol.getDefaultInstance()))
								.addLast(new ProtobufVarint32LengthFieldPrepender())
								.addLast(new ProtobufEncoder())
								.addLast(new ClientProtobufHandler());
					}
				});
		ChannelFuture future = bootstrap.connect("127.0.0.1", 9090).sync();
		log.info("client connect server.");
		future.channel().closeFuture().sync();
		group.shutdownGracefully();
	}
}
