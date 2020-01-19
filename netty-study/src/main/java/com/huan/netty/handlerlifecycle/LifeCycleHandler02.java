package com.huan.netty.handlerlifecycle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试生命周期函数
 *
 * @author huan.fu
 * @date 2019/1/28 - 15:47
 */
@Slf4j
public class LifeCycleHandler02 extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		log.info("LifeCycleHandler02.channelRegistered");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		log.info("LifeCycleHandler02.channelUnregistered");
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("LifeCycleHandler02.channelActive");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("LifeCycleHandler02.channelInactive");
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("LifeCycleHandler02.channelRead");
		String body = (String) msg;
		log.info("this is: {} times receive cliemt msg: {}", "LifeCycleHandler02", body);
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		log.info("LifeCycleHandler02.channelReadComplete");
		super.channelReadComplete(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		log.info("LifeCycleHandler02.userEventTriggered");
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		log.info("LifeCycleHandler02.channelWritabilityChanged");
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.info("LifeCycleHandler02.exceptionCaught");
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		log.info("LifeCycleHandler02.handlerAdded");
		super.handlerAdded(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		log.info("LifeCycleHandler02.handlerRemoved");
		super.handlerRemoved(ctx);
	}
}
