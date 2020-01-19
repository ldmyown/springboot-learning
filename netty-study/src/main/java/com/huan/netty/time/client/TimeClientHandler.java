package com.huan.netty.time.client;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @user 付焕
 * @date 2017/11/10 - 10:40 客户端处理服务器端发送过来的额数据
 */
@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

	private int counter;
	private byte[] req;

	public TimeClientHandler() {
		log.info("TimeClientHandler 被实例化.");
		try {
			req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * 客户端和服务器端TCP链路建立成功后，此方法被调用
	 */
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message;
		for (int i = 0; i < 100; i++) {
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}
	}

	@Override
	/**
	 * 收到服务器端的消息时触发
	 */
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String receive = (String) msg;
		log.info("client receive server msg : {},the counter is:{}", receive, ++counter);
	}

	@Override
	/**
	 * 发生异常时触发
	 */
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("client error:", cause);
		ctx.close();
	}
}
