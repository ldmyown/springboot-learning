package com.huan.netty.tasks.handler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.huan.netty.tasks.pack.Pack;
import com.huan.netty.tasks.provider.ProviderManager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务器获取到具体的报文消息，交给具体的业务类去进行处理
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月26日 - 下午8:31:33
 */
@Slf4j
public class PackServerHandler extends SimpleChannelInboundHandler<Pack> {

	private static Executor executor = Executors.newFixedThreadPool(50);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Pack msg) throws Exception {
		executor.execute(() -> {
			try {
				ProviderManager.invoked(ctx, msg);
			} catch (Exception e) {
				log.info("业务线程处理业务失败.", e);
			}
		});
	}

	/** 当发生了异常时，此方法调用 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("error:", cause);
		ctx.close();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			switch (event.state()) {
			case READER_IDLE:
				log.info("读空闲");
				// 关闭客户端通道...
				ctx.close();
				break;
			case WRITER_IDLE:
				log.info("写空闲");
				break;
			case ALL_IDLE:
				log.info("读写空闲");
				break;
			default:
				break;
			}
		}
	}

}
