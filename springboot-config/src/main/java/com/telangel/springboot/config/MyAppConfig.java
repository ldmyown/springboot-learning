package com.telangel.springboot.config;

import com.telangel.springboot.service.HelloService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author lid
 * @date   2020/1/14 11:31
 * @version 1.0.0
 * @since 1.0 
 */
@Configuration
public class MyAppConfig {

    /**
     * 将方法的返回值添加到容器中；容器中这个组件默认的id就是方法名,也可以通过指定name手动定义id
     */
    @Bean(name = "helloService21")
    public HelloService helloService2() {
        System.out.println("配置类@Bean给容器中添加组件了...");
        return new HelloService();
    }
}
