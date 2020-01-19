package com.huan.netty.protostuff;

import java.net.InetSocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务器端处理客户端发送过来的数据
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月21日 - 下午9:07:54
 */
@Slf4j
public class ProtoStuffServerHandler extends ChannelInboundHandlerAdapter {
	private int counter = 0;

	/**
	 * 接收到数据的时候调用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		log.info("读取客户端[{}]发送过来的数据.", remoteAddress.getAddress().getHostAddress());

		Person person = (Person) msg;
		log.info("当前是第[{}]次获取到客户端发送过来的person对象[{}].", ++counter, person);
	}

	/** 当发生了异常时，次方法调用 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("error:", cause);
		ctx.close();
	}
}
