package com.telangel.springbootmybatis.service.impl;

import com.telangel.springbootmybatis.entity.User;
import com.telangel.springbootmybatis.mapper.UserMapper;
import com.telangel.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0.0
 * @项目名称： springboot-mybatis
 * @类名称： UserServiceImpl
 * @类描述：
 * @author： lid
 * @date： 2019/2/20 14:20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    protected UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.delete(id);
    }
}
