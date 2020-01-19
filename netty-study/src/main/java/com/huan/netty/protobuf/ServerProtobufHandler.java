package com.huan.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 服务器端接收到客户端发送的请求，然后随机给客户端返回一个对象
 *
 * @author huan.fu
 * @date 2019/2/15 - 14:26
 */
@Slf4j
public class ServerProtobufHandler extends SimpleChannelInboundHandler<TaskProtobufWrapper.TaskProtocol> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TaskProtobufWrapper.TaskProtocol taskProtocol) {
		switch (taskProtocol.getPackType()) {
			case LOGIN:
				log.info("接收到一个登录类型的pack:[{}]", taskProtocol.getLoginPack().getUsername() + " : " + taskProtocol.getLoginPack().getPassword());
				break;
			case CREATE_TASK:
				log.info("接收到一个创建任务类型的pack:[{}]", taskProtocol.getCreateTaskPack().getTaskId() + " : " + taskProtocol.getCreateTaskPack().getTaskName());
				break;
			case DELETE_TASK:
				log.info("接收到一个删除任务类型的pack:[{}]", Arrays.toString(taskProtocol.getDeleteTaskPack().getTaskIdList().toArray()));
				break;
			default:
				log.error("接收到一个未知类型的pack:[{}]", taskProtocol.getPackType());
				break;
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
		log.error("发生异常", cause);
	}
}
