package com.telangel;

import com.telangel.service.HttpFileServer;
import com.telangel.service.StorageService;
import com.telangel.util.CacheUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootFileuploadWebApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringbootFileuploadWebApplication.class, args);
	}

	@Bean
	CommandLineRunner init(final StorageService storageService, final CacheUtils cacheUtils, HttpFileServer httpFileServer) {
		return  (args) -> {
			cacheUtils.init();
			storageService.init();
			// 启动文件下载服务
			httpFileServer.run();
		} ;
	}

}
