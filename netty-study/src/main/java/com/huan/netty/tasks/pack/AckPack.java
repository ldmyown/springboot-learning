package com.huan.netty.tasks.pack;

import lombok.Data;

/**
 * 通用消息应答,直接返回ACK
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月26日 - 下午8:49:50
 */
@Data
public class AckPack {

	private String ack = "ACK";
}
