package com.telangel.util;

import com.telangel.cache.ICache;
import com.telangel.cache.MapCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @auther lid
 * @date 2019/3/24 20:37
 * @version 1.0.0
 */
@Component
public class CacheUtils {

    public static ICache cache;

    /**
     * 缓存使用类型
     * 1: redis  2:map 默认redis
     */
    private static Integer cacheType;

    @Value("${breakpoint.upload.cache}")
    public void setPort(Integer cacheType) {
        CacheUtils.cacheType = cacheType;
    }

    public static void init() throws Exception {
        if (cacheType == null) {
            cacheType = 1;
        }
        if (cacheType == 1) {
//            cache = RedisCache.single();
        } else if (cacheType == 2) {
            cache = MapCache.single();
        } else {
            throw new Exception("不支持的缓存类型：" + cacheType);
        }
    }
}
