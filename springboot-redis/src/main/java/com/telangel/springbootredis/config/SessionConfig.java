package com.telangel.springbootredis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @version 1.0.0
 * @项目名称： springboot-redis
 * @类名称： SessionConfig
 * @类描述：
 * @author： lid
 * @date： 2019/2/15 17:39
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {
}
