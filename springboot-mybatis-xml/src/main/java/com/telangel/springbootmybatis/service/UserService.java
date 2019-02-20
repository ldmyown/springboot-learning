package com.telangel.springbootmybatis.service;

import com.telangel.springbootmybatis.entity.User;

/**
 * @version 1.0.0
 * @项目名称： springboot-mybatis
 * @类名称： UserService
 * @类描述：
 * @author： lid
 * @date： 2019/2/20 14:18
 */
public interface UserService {

    User getUserById(Long id);

    void updateUser(User user);

    void insertUser(User user);

    void deleteUserById(Long id);

}
