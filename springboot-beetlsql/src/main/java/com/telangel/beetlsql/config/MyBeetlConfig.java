package com.telangel.beetlsql.config;

import com.ibeetl.starter.BeetlTemplateCustomize;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * beetlsql 自定义属性
 * @author lid
 * @date   2019/4/17 10:29
 * @version 1.0.0
 */
@Configuration
public class MyBeetlConfig {

    @Value("${blog.title}")
    private String title;

    @Bean
    public BeetlTemplateCustomize beetlTemplateCustomize(){
        return (groupTemplate) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("blogSiteTitle", "LID的博客");
            groupTemplate.setSharedVars(map);
        };
    }
}
