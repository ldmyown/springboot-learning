package com.telangel.springboot.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
 *        prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 *   只有这个组件是容器中的组件，才能容器提供的@ConfigurationProperties功能；
 *  @author lid
 */
@Data
//@PropertySource(value = {"classpath:person.properties"})
@Component
@ConfigurationProperties(prefix = "person")
//@Validated
public class Person {
//    @Value("${person.last-name}")
//    @Email
    private String lastName;
//    @Value("#{10*2}")
    private Integer age;
    private Boolean boss;
    private Date birth;

    private Map<String, Object> maps;

    private List<Object> list;

    private Dog dog;
}
