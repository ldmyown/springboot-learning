package com.telangel.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lid
 * @date   2020/1/14 11:38
 * @version 1.0.0
 * @since 1.0 
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String sayHello(){
        return "Hello";
    }
}
