package com.telangel;

import com.telangel.service.HttpFileServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author lid
 */
@SpringBootApplication
public class SpringbootNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootNettyApplication.class, args);
    }

    @Bean
    CommandLineRunner init(final HttpFileServer httpFileServer) {
        return  (args) -> {
            // 启动文件下载服务
            httpFileServer.run();
        } ;
    }

}
