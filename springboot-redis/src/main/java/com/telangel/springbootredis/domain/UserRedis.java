package com.telangel.springbootredis.domain;

import com.alibaba.fastjson.JSONObject;
import com.telangel.springbootredis.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @项目名称： springboot-redis
 * @类名称： UserRedis
 * @类描述：
 * @author： lid
 * @date： 2019/2/15 16:06
 */
@Repository
public class UserRedis {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void add(String key, Long time, User user) {
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(user), time, TimeUnit.SECONDS);
    }

    public void add(String key, Long time, List<User> users) {
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(users), time, TimeUnit.SECONDS);
    }

    public User get(String key) {
        String userJson = redisTemplate.opsForValue().get(key);
        User user = null;
        if (!StringUtils.isEmpty(userJson)) {
            user = JSONObject.parseObject(userJson, User.class);
        }
        return user;
    }

    public List<User> getList(String key) {
        String usersJson = redisTemplate.opsForValue().get(key);
        List<User> users = null;
        if (!StringUtils.isEmpty(usersJson)) {
            users = (List<User>)JSONObject.parseObject(usersJson, List.class);
        }
        return users;
    }

    public void delete(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
