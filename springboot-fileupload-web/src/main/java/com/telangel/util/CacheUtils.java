package com.telangel.util;

import com.telangel.cache.ICache;
import com.telangel.cache.MapCache;
import com.telangel.cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lid
 * @date 2019/3/24 20:37
 * @version 1.0.0
 */
@Component
public class CacheUtils {

    public static ICache cache;

    @Autowired
    public RedisCache redisCache;

    @Autowired
    public MapCache mapCache;

    /**
     * 缓存使用类型
     * 1: redis  2:map 默认redis
     */
    @Value("${breakpoint.upload.cache}")
    private Integer cacheType;

//    @Value("${breakpoint.upload.cache}")
//    public void setPort(Integer cacheType) {
//        CacheUtils.cacheType = cacheType;
//    }

    public void init() throws Exception {
        if (cacheType == null) {
            cacheType = 1;
        }
        if (cacheType == 1) {
            cache = redisCache;
        } else if (cacheType == 2) {
            cache = mapCache;
        } else {
            throw new Exception("不支持的缓存类型：" + cacheType);
        }
    }
}
