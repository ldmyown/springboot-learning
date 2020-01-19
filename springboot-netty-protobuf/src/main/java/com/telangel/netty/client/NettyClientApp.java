package com.telangel.netty.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
* Netty 客户端主程序
* @author lid
* @date 2020/1/16 10:45
* @version 1.0.0
*/
@SpringBootApplication
public class NettyClientApp implements CommandLineRunner {

	@Autowired
	private NettyClient nettyClient;

	public static void main(String[] args) {
		// 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
		SpringApplication.run(NettyClientApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		nettyClient.connect();
	}
}
