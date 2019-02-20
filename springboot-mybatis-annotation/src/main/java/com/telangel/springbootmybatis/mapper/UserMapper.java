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

    @Select("select * from t_user where user_id = #{id}")
    @Results({
            @Result(property = "userName",  column = "user_name"),
            @Result(property = "userPassword", column = "user_password"),
            @Result(property = "userId", column = "user_id")
    })
    User getUserById(Long id);

    @Insert("insert into t_user(user_name, user_password, sex, age) values(#{userName}, #{userPassword}, #{sex}, #{age})")
    void insert(User user);

    @Update("update t_user set user_name = #{userName}, user_password = #{userPassword}, sex = #{sex}, age = #{age} where user_id = #{userId}")
    void update(User user);

    @Delete("delete from t_user where user_id = #{userId}")
    void delete(Long id);
}
