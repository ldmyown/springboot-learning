package com.huan.netty.tasks.provider;

import java.util.ArrayList;
import java.util.List;

import com.huan.netty.tasks.pack.Pack;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 管理系统中的所有的pack provider
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月26日 - 下午9:04:41
 */
@Slf4j
public class ProviderManager {

	private static List<PackProvider> providers;

	static {
		providers = new ArrayList<>(2);
		providers.add(new AuthPackProvider());
		providers.add(new TaskPackProvider());
	}

	public static void invoked(ChannelHandlerContext context, Pack pack) {
		boolean isFund = false;
		for (PackProvider provider : providers) {
			if (provider.support(pack)) {
				provider.invoked(context, pack);
				isFund = true;
			}
			if (isFund) {
				break;
			}
		}
		if (!isFund) {
			log.error("当前没有可以处理报文[{}]的packProvider", pack);
		}
	}

}
