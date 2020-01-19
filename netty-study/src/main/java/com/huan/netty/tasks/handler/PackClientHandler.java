package com.huan.netty.tasks.handler;

import java.net.InetSocketAddress;

import com.huan.netty.tasks.pack.AuthPack;
import com.huan.netty.tasks.pack.Pack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PackClientHandler extends SimpleChannelInboundHandler<Pack> {
	/**
	 * 客户端和服务器端TCP链路建立成功后，此方法被调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		AuthPack authPack = new AuthPack();
		InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().localAddress();
		String address = socketAddress.getAddress().getHostAddress();
		String auth = address + "12345";
		authPack.setAuth(auth);
		Pack pack = new Pack();
		pack.setPackType("auth");
		pack.setBody(authPack);
		ctx.writeAndFlush(pack);
	}

	/**
	 * 发生异常时调用
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("client error:", cause);
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Pack msg) throws Exception {
		log.info("客户端收到服务器端发送的消息[{}]", msg);
	}

}