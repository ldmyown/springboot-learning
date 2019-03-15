package com.telangel.service;

import com.telangel.protobuf.User;

/**
 * @version 1.0.0
 * @项目名称： springboot-protobuf
 * @类名称： UserService
 * @类描述：
 * @author： lid
 * @date： 2019/3/12 18:14
 */
public interface UserService {

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    User.UserResponse findUserByName(String username);

}
