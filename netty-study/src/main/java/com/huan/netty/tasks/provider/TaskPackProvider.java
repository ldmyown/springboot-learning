package com.huan.netty.tasks.provider;

import java.util.Objects;

import com.huan.netty.tasks.pack.AckPack;
import com.huan.netty.tasks.pack.Pack;
import com.huan.netty.tasks.pack.TaskPack;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理业务逻辑
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月26日 - 下午8:57:23
 */
@Slf4j
public class TaskPackProvider implements PackProvider {

	@Override
	public boolean support(Pack pack) {
		return Objects.equals("task", pack.getPackType());
	}

	@Override
	public void invoked(ChannelHandlerContext context, Pack pack) {
		Pack ackPack = new Pack();
		ackPack.setPackType("ack");
		ackPack.setBody(new AckPack());
		context.writeAndFlush(ackPack);
		TaskPack taskPack = (TaskPack) pack.getBody();
		log.info("服务器端接收到客户端传递多来的消息为:[{}]", taskPack);
	}

}
