package com.telangel.netty.server;

import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Netty 服务端主程序
 * @version :1.0.0
 * @author lid
 * @date 2020/1/16 10:41
 */
@SpringBootApplication
public class NettyServerApp implements CommandLineRunner {

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(NettyServerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture channelFuture = nettyServer.start();
        // 服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }
}
