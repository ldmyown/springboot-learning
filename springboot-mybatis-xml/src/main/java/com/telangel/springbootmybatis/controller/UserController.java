package com.telangel.springbootmybatis.controller;

import com.telangel.springbootmybatis.entity.User;
import com.telangel.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0.0
 * @项目名称： springboot-mybatis
 * @类名称： UserController
 * @类描述：
 * @author： lid
 * @date： 2019/2/20 14:22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    protected UserService userService;

    // http://127.0.0.1:8080/user/getUser?id=1
    @RequestMapping("/getUser")
    public User getUser(Long id){
        return userService.getUserById(id);
    }

    // http://127.0.0.1:8080/user/addUser?userName=aa&userPassword=123456&sex=1&age=10
    @RequestMapping("/addUser")
    public String addUser(User user){
        userService.insertUser(user);
        return "添加用户成功";
    }

    // http://127.0.0.1:8080/user/updateUser?userId=1&userName=aabd&userPassword=123456&sex=1&age=10
    @RequestMapping("/updateUser")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "更新用户成功";
    }
    // http://127.0.0.1:8080/user/delUser/1
    @RequestMapping("/delUser/{id}")
    public String delUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "删除用户成功";
    }
}
