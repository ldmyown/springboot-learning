package com.huan.netty.tasks.provider;

import java.net.InetSocketAddress;
import java.util.Objects;

import com.huan.netty.tasks.pack.AckPack;
import com.huan.netty.tasks.pack.AuthPack;
import com.huan.netty.tasks.pack.Pack;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 进行任务处理
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月26日 - 下午8:44:57
 */
@Slf4j
public class AuthPackProvider implements PackProvider {

	@Override
	public boolean support(Pack pack) {
		return Objects.equals("auth", pack.getPackType());
	}

	@Override
	public void invoked(ChannelHandlerContext context, Pack pack) {
		AuthPack authPack = (AuthPack) pack.getBody();
		InetSocketAddress remoteAddress = (InetSocketAddress) context.channel().remoteAddress();
		String clientIp = remoteAddress.getAddress().getHostAddress();
		if (Objects.equals(authPack.getAuth(), clientIp + "12345")) {
			log.info("客户端[{}]认证成功.", clientIp);
			Pack ackPack = new Pack();
			ackPack.setPackType("ack");
			ackPack.setBody(new AckPack());
			context.writeAndFlush(ackPack);
		} else {
			log.info("客户端[{}]非法请求.", clientIp);
			context.close();
		}
	}

}
