package com.telangel.netty.client;

import com.telangel.netty.client.handle.ClientChannelHandler;
import com.telangel.protobuf.Kncp;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 通道初始化，主要用于设置各种Handler
 *
 * @author lid
 * @date: 2018-11-28 14:55
 */
@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    ClientChannelHandler clientChannelHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        pipeline.addLast("idleStateHandler",
                new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
        //传输的协议 Protobuf
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(Kncp.ProtoMessage.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        //自定义Handler
        pipeline.addLast("clientChannelHandler", clientChannelHandler);
    }
}