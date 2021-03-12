package com.gzz.boot.mybatis.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * redis实现mybatis二级缓存
 * 使用方法：
 * 1，在启动类加上注解@EnableCaching
 * 2，mapper类加上注解@CacheNamespace(implementaction=com.gzz.boot.mybatis.cache.RedisCache.class)
 */
@Slf4j
public class RedisCache implements Cache {
    private static  RedisTemplate<String, Object> redisTemplate;
    //缓存对象唯一标识, orm的框架都是按对象的方式缓存，而每个对象都需要一个唯一标识.
    private String id;
    //用于事务性缓存操作的读写锁 处理事务性缓存中做的
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    //缓存对象的时间，3分钟
    private final static int EXPRIRE_TIME_IN_MINUT = 3;

    public RedisCache(){ }
    public static void setRedisTemplate(RedisTemplate redisTemplate){
        RedisCache.redisTemplate =redisTemplate;
    }
    //构造方法---把对象唯一标识传进来
    public RedisCache(String id){
        if(id == null){
            throw new IllegalArgumentException("缓存对象id是不能为空的");
        }
        this.id = id;
    }
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        try{
            System.out.println(key.toString());
            //使用redisTemplate得到值操作对象
            ValueOperations operation = redisTemplate.opsForValue();
            //使用值操作对象operation设置缓存对象
            operation.set(key.toString(),value,EXPRIRE_TIME_IN_MINUT, TimeUnit.MINUTES);
            log.debug("缓存对象保存成功");
        }catch (Throwable t){
            log.error("缓存对象保存失败"+t);
        }
    }

    @Override
    public Object getObject(Object key) {
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            Object result = operations.get(key.toString());
            log.debug("获取缓存对象");
            return result;
        }catch (Throwable t){
            log.error("缓存对象获取失败"+t);
            return null;
        }
    }

    @Override
    public Object removeObject(Object key) {
        try{
            redisTemplate.delete(key.toString());
            log.debug("删除缓存对象成功！");
        }catch (Throwable t){
            log.error("删除缓存对象失败！"+t);
        }
        return null;
    }

    @Override
    public void clear() {
        //回调函数
        redisTemplate.execute((RedisCallback) collection->{
            collection.flushDb();
            return  null;
        });
        log.debug("清空缓存对象成功！");
    }

    @Override
    public int getSize() {
        Long size = redisTemplate.execute(new RedisCallback<Long>(){
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
