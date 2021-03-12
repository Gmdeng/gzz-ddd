package com.gzz.boot.aop.resubmit;

import org.springframework.util.DigestUtils;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 重复提交锁
 * 定时任务锁。
 * 使用
 */
public class ResubmitLimitLock {
   private static  final ConcurrentHashMap<String, Object> LOCK_CACHE = new ConcurrentHashMap<>(200);
   private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5, new ThreadPoolExecutor.DiscardPolicy());

   private ResubmitLimitLock(){}

    /**
     * 静态内部类， 单例模式
     */
   private static class SingletonInstance{
       private static final ResubmitLimitLock INSTANCE = new ResubmitLimitLock();
   }

   public static ResubmitLimitLock getInstance(){
       return SingletonInstance.INSTANCE;
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
       return Objects.isNull( LOCK_CACHE.putIfAbsent(key, value));
   }

    /**
     * 延时释放锁，用以控制短时间内的重复提交
     * @param lock  是否需要解锁
     * @param key   对应KEY
     * @param delaySeconds 延时时间
     */
   public void unLock(final boolean lock, final String key, final int delaySeconds){
       if(lock) {
           executor.schedule(()->{
              LOCK_CACHE.remove(key);
           }, delaySeconds, TimeUnit.SECONDS);
       }
   }
}
