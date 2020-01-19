package com.telangel.springboot;

import com.telangel.springboot.bean.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;

@SpringBootTest
class ConfigApplicationTests {

    @Autowired
    Person person;

    @Autowired
    ApplicationContext act;

    @Test
    void test1() {
        System.out.println(act.containsBean("helloService21"));
    }

    @Test
    void contextLoads() {
        System.out.println(person);
    }
}
