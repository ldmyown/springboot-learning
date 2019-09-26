package com.telangel.service;

import com.telangel.init.FileServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @version 1.0.0
 * @项目名称： springboot-netty
 * @类名称： HttpFileServer
 * @类描述：
 * @author： lid
 * @date： 2019/3/13 16:02
 */
@Service
public class HttpFileServer {

    @Value("${breakpoint.upload.dir}")
    public String webroot;
    @Value("${breakpoint.download.port}")
    public int port;

    private static Logger logger = LoggerFactory.getLogger(HttpFileServer.class);

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new FileServerInitializer(webroot));
            ChannelFuture future = bootstrap.bind(port).sync();
           logger.info("服务器已启动>>网址:" + "0.0.0.0:" + port);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
