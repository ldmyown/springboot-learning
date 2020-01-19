package com.huan.netty.tasks.decoder;

import java.util.List;

import com.huan.netty.tasks.pack.Pack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 解码器-将字节数组解码成Pack对象
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月26日 - 下午8:28:33
 */
public class PackDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Schema<Pack> schema = RuntimeSchema.getSchema(Pack.class);
		Pack person = schema.newMessage();
		byte[] array = new byte[in.readableBytes()];
		in.readBytes(array);
		ProtobufIOUtil.mergeFrom(array, person, schema);
		out.add(person);
	}
}
