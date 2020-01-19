package com.telangel.controller;

import com.telangel.entity.TbKofDeptparam;
import com.telangel.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class HelloWorldController {

    private static Integer number = 1;

    @Autowired
    private TestService testService;

    @GetMapping("hello")
    public String helloWorld(){
        log.info("请求hello");
//        TbKofDeptparam deptparam = testService.getById(78401);
//        deptparam.setQuota(deptparam.getQuota() + 1);
//        testService.updateById(deptparam);
        number++;
        return "Hello World" + number;
    }
}
