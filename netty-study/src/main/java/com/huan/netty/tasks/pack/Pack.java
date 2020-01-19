package com.huan.netty.tasks.pack;

import lombok.Data;

/**
 * 服务器端和客户端发送和接收同一发送这个报文消息
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月26日 - 下午8:16:47
 */
@Data
public class Pack {
	/** 包的类型 */
	private String packType;
	/** 报文体，此字段可以存放具体的业务报文 */
	private Object body;
}
