package com.telangel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0.0
 * @项目名称： springboot-helloworld
 * @类名称： HelloWorldController
 * @类描述：
 * @author： lid
 * @date： 2019/2/15 13:23
 */
@RestController
public class HelloWorldController {

    @GetMapping("hello")
    public String helloWorld(){
        return "Hello World";
    }
}
