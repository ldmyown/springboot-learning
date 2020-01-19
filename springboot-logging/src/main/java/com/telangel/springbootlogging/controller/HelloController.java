package com.telangel.springbootlogging.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lid
 * @date   2020/1/15 18:50
 * @version 1.0.0
 * @since 1.0 
 */
@RestController
public class HelloController {
    Logger logger = LoggerFactory.getLogger(getClass());

    String test = "# **一、**Spring Boot 入门\n" +
            "\n" +
            "## 1、Spring Boot 简介\n" +
            "\n" +
            "> 简化Spring应用开发的一个框架；\n" +
            ">\n" +
            "> 整个Spring技术栈的一个大整合；\n" +
            ">\n" +
            "> J2EE开发的一站式解决方案；\n" +
            "\n" +
            "## 2、微服务\n" +
            "\n" +
            "2014，martin fowler\n" +
            "\n" +
            "微服务：架构风格（服务微化）\n" +
            "\n" +
            "一个应用应该是一组小型服务；可以通过HTTP的方式进行互通；\n" +
            "\n" +
            "单体应用：ALL IN ONE\n" +
            "\n" +
            "微服务：每一个功能元素最终都是一个可独立替换和独立升级的软件单元；\n" +
            "\n" +
            "[详细参照微服务文档](https://martinfowler.com/articles/microservices.html#MicroservicesAndSoa)\n" +
            "\n" +
            "\n" +
            "\n" +
            "## 3、环境准备\n" +
            "\n" +
            "http://www.gulixueyuan.com/ 谷粒学院\n" +
            "\n" +
            "环境约束\n" +
            "\n" +
            "–jdk1.8：Spring Boot 推荐jdk1.7及以上；java version \"1.8.0_112\"\n" +
            "\n" +
            "–maven3.x：maven 3.3以上版本；Apache Maven 3.3.9\n" +
            "\n" +
            "–IntelliJIDEA2017：IntelliJ IDEA 2017.2.2 x64、STS\n" +
            "\n" +
            "–SpringBoot 1.5.9.RELEASE：1.5.9；\n" +
            "\n" +
            "统一环境；\n" +
            "\n" +
            "\n" +
            "\n" +
            "### 1、MAVEN设置；\n" +
            "\n" +
            "给maven 的settings.xml配置文件的profiles标签添加\n" +
            "\n" +
            "```xml\n" +
            "<profile>\n" +
            "  <id>jdk-1.8</id>\n" +
            "  <activation>\n" +
            "    <activeByDefault>true</activeByDefault>\n" +
            "    <jdk>1.8</jdk>\n" +
            "  </activation>\n" +
            "  <properties>\n" +
            "    <maven.compiler.source>1.8</maven.compiler.source>\n" +
            "    <maven.compiler.target>1.8</maven.compiler.target>\n" +
            "    <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>\n" +
            "  </properties>\n" +
            "</profile>\n" +
            "```\n" +
            "\n" +
            "### 2、IDEA设置\n" +
            "\n" +
            "整合maven进来；\n" +
            "\n" +
            "![idea设置](images/搜狗截图20180129151045.png)\n" +
            "\n" +
            "\n" +
            "\n" +
            "![images/](images/搜狗截图20180129151112.png)\n" +
            "\n" +
            "## 4、Spring Boot HelloWorld\n" +
            "\n" +
            "一个功能：\n" +
            "\n" +
            "浏览器发送hello请求，服务器接受请求并处理，响应Hello World字符串；\n" +
            "\n" +
            "\n" +
            "\n" +
            "### 1、创建一个maven工程；（jar）\n" +
            "\n" +
            "### 2、导入spring boot相关的依赖\n";

    @RequestMapping("hello")
    public String Hello(){
        for (int i = 0; i < 100; i++) {
            logger.info("这是info日志" + test);
        }
        return "Hello";
    }
}
