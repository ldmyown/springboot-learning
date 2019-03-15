package com.telangel.service.impl;

import com.telangel.data.UserData;
import com.telangel.protobuf.User;
import com.telangel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0.0
 * @项目名称： springboot-protobuf
 * @类名称： UserServiceImpl
 * @类描述：
 * @author： lid
 * @date： 2019/3/12 18:16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserData userData;

    @Override
    public User.UserResponse findUserByName(String username) {
        return userData.getUserData().get(username);
    }
}
