package com.huan.netty.protostuff;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtostuffClientHandler extends ChannelInboundHandlerAdapter {
	/**
	 * 客户端和服务器端TCP链路建立成功后，此方法被调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Person person;
		for (int i = 0; i < 100; i++) {
			person = new Person();
			person.setId(i);
			person.setName("张三" + i);
			ctx.writeAndFlush(person);
		}
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