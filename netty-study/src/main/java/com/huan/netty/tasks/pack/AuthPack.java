package com.huan.netty.tasks.pack;

import lombok.Data;

/**
 * 认证消息包
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月26日 - 下午8:21:31
 */
@Data
public class AuthPack {

	/** 客户端自己的ip地址+12345进行发送即可 */
	private String auth;

}
