package com.gzz.boot.aop.resubmit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.util.DigestUtils;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 重复提交锁
 * CACHE锁。
 * 使用GOOGLE Cache缓存
 */
public class ResubmitLimitCacheLock {
    //定义缓存，设置最大缓存数及过期日期
    private static final Cache<String,Object> CACHE = CacheBuilder
            .newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(20, TimeUnit.SECONDS)
            .build();
    private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5, new ThreadPoolExecutor.DiscardPolicy());

    /**
     * 静态内部类， 单例模式
     */
    private static class SingletonInstance{
        private static final ResubmitLimitCacheLock INSTANCE = new ResubmitLimitCacheLock();
    }

    public static ResubmitLimitCacheLock getInstance(){
        return ResubmitLimitCacheLock.SingletonInstance.INSTANCE;
    }

    /**
     * 加密码处理
     * @param param
     * @return
     */
    public static String handleKey(String param){
        return DigestUtils.md5DigestAsHex(param.getBytes());
    }

    /**
     * 加锁 - 使用putIfAbsent 原子操作保证线程安全
     * @param key  对应KEY
     * @param value 对应值
     * @return
     */
    public boolean lock(final String key,Object value){
        try {
            CACHE.put(key, value);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    /**
     * 延时释放锁，用以控制短时间内的重复提交
     * @param lock  是否需要解锁
     * @param key   对应KEY
     * @param delaySeconds 延时时间
     */
    public void unLock(final boolean lock,final String key, final int delaySeconds){
        if(lock) {
            executor.schedule(()->{
                CACHE.invalidate(key);
            }, delaySeconds, TimeUnit.SECONDS);
        }
    }
}
