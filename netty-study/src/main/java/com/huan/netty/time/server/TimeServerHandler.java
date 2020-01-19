package com.huan.netty.time.server;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @user 付焕
 * @date 2017/11/10 - 9:51 接收客户端的消息，实现对网络事件的读写操作
 */
@Slf4j
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	public TimeServerHandler() {
		log.info("---------------> " + TimeServerHandler.class + " ------->被实例化");
	}

	private int counter;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String receiveMsg = ((String) msg);
		log.info("server receive client order:{};the counter is:{}", msg, ++counter);
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(receiveMsg) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
		currentTime = currentTime + System.getProperty("line.separator");
		ByteBuf response = Unpooled.copiedBuffer(currentTime.getBytes("UTF-8"));
		// 数据写入到了缓冲区数组中
		ctx.write(response);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 将缓冲区的消息写入到SocketChannel中
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		log.error("TimeServerHandler error.", cause);
	}
}
