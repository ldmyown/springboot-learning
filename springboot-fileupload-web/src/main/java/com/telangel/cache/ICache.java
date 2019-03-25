package com.telangel.cache;

/**
 * @auther lid
 * @date 2019/3/24 17:25
 */
public interface ICache<T> {


    T get(String key);

    T hget(String key, String field);

    void set(String key, T value);

    void set(String key, T value, long expired);

    void hset(String key, String field, Object value);

    void hset(String key, String field, Object value, long expired);

    void del(String key);

    boolean hasKey(String key);

    boolean hHasKey(String key, String field);

    void hdel(String key, String field);

    void clean();
}
