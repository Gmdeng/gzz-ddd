package com.gzz.boot.mybatis.cache;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * 传参到缓存
 */
public class RedisCacheTransfer {

    public RedisCacheTransfer(RedisTemplate<String, Object> redisTemplate) {
        RedisCache.setRedisTemplate(redisTemplate);
    }
}
