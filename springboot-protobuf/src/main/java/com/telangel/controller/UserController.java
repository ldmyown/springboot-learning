package com.telangel.controller;

import com.telangel.protobuf.User;
import com.telangel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @version 1.0.0
 * @项目名称： springboot-protobuf
 * @类名称： UserLoginController
 * @类描述：
 * @author： lid
 * @date： 2019/3/12 18:06
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "getUser", produces = "application/x-protobuf")
    public User.UserResponse getUser(@RequestBody User.UserRequest request) {
        String username = request.getUsername();
        User.UserResponse userResponse = userService.findUserByName(username);
        return userResponse;
    }
}
