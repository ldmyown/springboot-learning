package com.huan.netty.protostuff;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 将一个字节数据转换成Person对象
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月21日 - 下午8:50:38
 */
public class ProtostuffDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Schema<Person> schema = RuntimeSchema.getSchema(Person.class);
		Person person = schema.newMessage();
		byte[] array = new byte[in.readableBytes()];
		in.readBytes(array);
		ProtobufIOUtil.mergeFrom(array, person, schema);
		out.add(person);
	}
}
