package com.huan.netty.tasks.encoder;

import com.huan.netty.tasks.pack.Pack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 将Pack对象转换成字节数组
 * 
 * @描述
 * @作者 huan
 * @时间 2017年12月26日 - 下午8:30:12
 */
public class PackEncoder extends MessageToByteEncoder<Pack> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Pack msg, ByteBuf out) throws Exception {
		LinkedBuffer buffer = LinkedBuffer.allocate(1024);
		Schema<Pack> schema = RuntimeSchema.getSchema(Pack.class);
		byte[] array = ProtobufIOUtil.toByteArray(msg, schema, buffer);
		out.writeBytes(array);
	}

}
