package com.gzz.boot.mybatis.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 缓存配置，使用redis作为缓存服务器
 */
@Slf4j
public class MybatisPlusCache implements Cache {
    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    //这里使用了redis缓存，使用springboot自动注入
    private RedisTemplate<String, Object> redisTemplate;
    private String id;

    //返回cache的唯一名称
    @Override
    public String getId() {
        return this.id;
    }

    //缓存存值
    @Override
    public void putObject(Object key, Object value) {
        //id是namespace的值，key是方法名，value是查询的结果
        redisTemplate.opsForHash().put(id, key.toString(), value);
    }

    //缓存取值
    @Override
    public Object getObject(Object key) {
        return redisTemplate.opsForHash().get(id, key.toString());
    }

    //mybatis保留方法
    @Override
    public Object removeObject(Object key) {
        // redisTemplate.delete(id);
        return null;
    }

    //清空缓存，在增删改时会自动调用
    @Override
    public void clear() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.flushDb();
                return null;
            }
        });
    }

    @Override
    public int getSize() {
        return redisTemplate.opsForHash().size(id).intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    //获取RedisTemplate,不能通过注入的方式，原因是此类是由mybatis实例化的
//    private RedisTemplate getRedisTemplate() {
//        //从上下文中获取redisTemplate
//        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContext.("redisTemplate");
//        //设置key是string类型的序列
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        //设置hashKey是string类型的序列
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }
}
