package com.huan.netty.tasks.provider;

import com.huan.netty.tasks.pack.Pack;

import io.netty.channel.ChannelHandlerContext;

/**
 * 报文的处理提供者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月26日 - 下午8:42:12
 */
public interface PackProvider {

	/** 判断是否有能力进行处理该报文 */
	boolean support(Pack pack);

	/** 进行具体的报文处理 */
	void invoked(ChannelHandlerContext context, Pack pack);

}
