package com.huan.netty.tasks.start;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.huan.netty.tasks.decoder.PackDecoder;
import com.huan.netty.tasks.encoder.PackEncoder;
import com.huan.netty.tasks.handler.PackClientHandler;
import com.huan.netty.tasks.pack.Pack;
import com.huan.netty.tasks.pack.TaskPack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * @user 付焕
 * @date 2017/11/10 - 15:29
 */
@Slf4j
public class NettyClient {

	private static final AtomicLong TASK_ID = new AtomicLong(1);

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)//
				.channel(NioSocketChannel.class)//
				.option(ChannelOption.TCP_NODELAY, true)//
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new LengthFieldPrepender(4));
						ch.pipeline().addLast(new PackEncoder());
						ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(10240, 0, 4, 0, 4));
						ch.pipeline().addLast(new PackDecoder());
						ch.pipeline().addLast(new PackClientHandler());
					}
				});
		ChannelFuture future = bootstrap.connect("localhost", 9091).sync();
		log.info("client connect server.");

		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		newSingleThreadScheduledExecutor.scheduleWithFixedDelay(() -> {
			TaskPack task = new TaskPack();
			task.setTaskId(TASK_ID.getAndIncrement());
			task.setTaskData("task data");
			Pack pack = new Pack();
			pack.setPackType("task");
			pack.setBody(task);
			future.channel().writeAndFlush(pack);
		}, 0, 1, TimeUnit.SECONDS);

		future.channel().closeFuture().sync();
		newSingleThreadScheduledExecutor.shutdown();
		group.shutdownGracefully();
	}
}
