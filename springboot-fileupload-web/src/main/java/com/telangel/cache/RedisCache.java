package com.telangel.cache;

import com.alibaba.fastjson.JSONObject;
import com.telangel.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * redis 缓存工具
 * @auther lid
 * @date 2019/3/24 16:45
 * @version 1.0.0
 */
@Component
public class RedisCache implements ICache{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 读取一个缓存
     * @param key 缓存key
     * @return
     */
    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 读取一个hash类型缓存
     *
     * @param key   缓存key
     * @param field 缓存field
     * @return
     */
    @Override
    public Object hget(String key, String field) {
        return stringRedisTemplate.opsForHash().get(key, field);
    }

    /**
     * 设置一个缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    @Override
    public void set(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(value));
    }

    /**
     * 设置一个缓存并带过期时间
     *
     * @param key     缓存key
     * @param value   缓存value
     * @param expired 过期时间，单位为秒
     */
    @Override
    public void set(String key, Object value, long expired) {
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(value), expired);
    }

    /**
     * 设置一个hash缓存
     *
     * @param key   缓存key
     * @param field 缓存field
     * @param value 缓存value
     */
    @Override
    public void hset(String key, String field, Object value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 设置一个hash缓存并带过期时间
     *
     * @param key     缓存key
     * @param field   缓存field
     * @param value   缓存value
     * @param expired 过期时间，单位为秒
     */
    @Override
    public void hset(String key, String field, Object value, long expired) {
        stringRedisTemplate.opsForHash().put(key, field, value);
        stringRedisTemplate.expire(key, expired, TimeUnit.SECONDS);
    }

    /**
     * 根据key删除缓存
     *
     * @param key 缓存key
     */
    @Override
    public void del(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 是否已存在key
     * @param key
     */
    @Override
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 是否已经存在hash key
     * @param key
     * @param field
     * @return
     */
    @Override
    public boolean hHasKey(String key, String field) {
        return stringRedisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 根据key和field删除缓存
     *
     * @param key   缓存key
     * @param field 缓存field
     */
    @Override
    public void hdel(String key, String field) {
        stringRedisTemplate.opsForHash().delete(key, field);
    }

    /**
     * 清空缓存
     */
    @Override
    public void clean() {
        stringRedisTemplate.delete(Constants.FILE_MD5_KEY);
        stringRedisTemplate.delete(Constants.FILE_UPLOAD_STATUS);
    }

}
