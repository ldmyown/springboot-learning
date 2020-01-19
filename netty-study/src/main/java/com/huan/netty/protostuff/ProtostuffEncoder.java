package com.huan.netty.protostuff;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 将Person对象转换成byte字节数组
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月21日 - 下午9:00:05
 */
public class ProtostuffEncoder extends MessageToByteEncoder<Person> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {
		LinkedBuffer buffer = LinkedBuffer.allocate(1024);
		Schema<Person> schema = RuntimeSchema.getSchema(Person.class);
		byte[] array = ProtobufIOUtil.toByteArray(msg, schema, buffer);
		out.writeBytes(array);
	}

}
