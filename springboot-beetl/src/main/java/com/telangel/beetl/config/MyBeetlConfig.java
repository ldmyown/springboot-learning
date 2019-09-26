package com.telangel.beetl.config;

import com.ibeetl.starter.BeetlTemplateCustomize;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * beetl 自定义属性
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
