package com.gzz.boot.shiro;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * 实现用户角色、权限缓存管理
 */
public class RedisCacheManager implements CacheManager {
    private static final String CACHE_KEY = "redis-cache";
    private final Logger logger = LogManager.getLogger();
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache<K, V>();
    }

    class RedisCache<K, V> implements Cache<K, V> {


        @Override
        public void clear() throws CacheException {
            redisTemplate.delete(CACHE_KEY);
        }

        private String toString(Object obj) {
            if (obj instanceof String) {
                return obj.toString();
            } else {
                return JSONObject.toJSONString(obj);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public V get(K k) throws CacheException {
            logger.info("get field:{}", toString(k));
            return (V) redisTemplate.boundHashOps(CACHE_KEY).get(k);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Set<K> keys() {
            logger.info("keys");
            return (Set<K>) redisTemplate.boundHashOps(CACHE_KEY).keys();
        }

        @Override
        public V put(K k, V v) throws CacheException {
            logger.info("put field:{}, value:{}", toString(k), toString(v));
            redisTemplate.boundHashOps(CACHE_KEY).put(k, v);
            return v;
        }

        @Override
        public V remove(K k) throws CacheException {
            logger.info("remove field:{}", toString(k));
            V v = get(k);
            redisTemplate.boundHashOps(CACHE_KEY).delete(k);
            return v;
        }

        @Override
        public int size() {
            int size = redisTemplate.boundHashOps(CACHE_KEY).size().intValue();
            logger.info("size:{}", size);
            return size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<V> values() {
            logger.info("values");
            return (Collection<V>) redisTemplate.boundHashOps(CACHE_KEY).values();
        }

        public String getCacheKey() {
            return "RedisCache";
        }
    }
}
