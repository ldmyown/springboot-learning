package com.telangel.springbootmybatis.mapper;

import com.telangel.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * @version 1.0.0
 * @项目名称： springboot-mybatis
 * @类名称： UserMapper
 * @类描述：
 * @author： lid
 * @date： 2019/2/20 13:53
 */
//@Mapper
public interface UserMapper {

    User getUserById(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);
}
