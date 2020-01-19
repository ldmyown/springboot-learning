package com.telangel.service;

import com.telangel.init.FileServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 1.0.0
 * @项目名称： springboot-netty
 * @类名称： HttpFileServer
 * @类描述：
 * @author： lid
 * @date： 2019/3/13 16:02
 */
public class HttpFileServer {

    public static String WEBROOT = "/root";
    public static int PORT = 8080;

    private static Logger logger = LoggerFactory.getLogger(HttpFileServer.class);

    public void run(final int port, final String url) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new FileServerInitializer(url));
            ChannelFuture future = bootstrap.bind(port).sync();
           logger.info("服务器已启动>>网址:" + "127.0.0.1:" + port);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main() throws InterruptedException {

        new HttpFileServer().run(PORT, WEBROOT);
    }

}
