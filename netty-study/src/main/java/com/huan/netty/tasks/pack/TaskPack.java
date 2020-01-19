package com.huan.netty.tasks.pack;

import lombok.Data;

/**
 * 任务报文
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月26日 - 下午8:26:44
 */
@Data
public class TaskPack {
	private Long taskId;
	private String taskData;
}
