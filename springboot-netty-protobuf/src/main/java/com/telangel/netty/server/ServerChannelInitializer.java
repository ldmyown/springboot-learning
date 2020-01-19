package com.telangel.netty.server;

import com.telangel.netty.server.handle.ServerChannelHandler;
import com.telangel.protobuf.Kncp;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 通道初始化，主要用于设置各种Handler
 *
 * @author: lid
 * @date: 2018-11-28 14:55
 **/
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    ServerChannelHandler serverChannelHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //IdleStateHandler心跳机制,如果超时触发Handle中userEventTrigger()方法
        // 入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        pipeline.addLast("idleStateHandler",
                new IdleStateHandler(15, 0, 0, TimeUnit.SECONDS));
        // 解码和编码，应和客户端一致
//        //传输的协议 Protobuf
//        pipeline.addLast(new ProtobufVarint32FrameDecoder());
//        pipeline.addLast(new ProtobufDecoder(Kncp.ProtoMessage.getDefaultInstance()));
//        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
//        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        //自定义Handler
        pipeline.addLast("serverChannelHandler", serverChannelHandler);
    }
}