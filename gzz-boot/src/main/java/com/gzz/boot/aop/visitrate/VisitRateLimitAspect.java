package com.gzz.boot.aop.visitrate;


import com.google.common.util.concurrent.RateLimiter;
import com.gzz.boot.aop.resubmit.ResubmitLimit;
import com.gzz.core.response.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 访问每秒限流  切面 处理
 */
@Slf4j
@Aspect
@Order(3)
public class VisitRateLimitAspect {
    //用来存放不同接口的RateLimiter(key为接口名称，value为RateLimiter)
    private ConcurrentHashMap<String, RateLimiter> map = new ConcurrentHashMap<>();

    private RateLimiter rateLimiter;

    // 创建一个限流器，设置每秒放置的令牌数为5个。
    // 返回的RateLimiter对象可以保证1秒内不会给超过5个令牌
    RateLimiter r = RateLimiter.create(5);
    // RateLimiter r = RateLimiter.create(2, 3, TimeUnit.SECONDS);
    @Around("@annotation(com.gzz.boot.aop.visitrate.VisitRateLimit)")
    public Object handleResubmit(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取注解信息
        VisitRateLimit annotation = method.getAnnotation(VisitRateLimit.class);

        int limitNum = annotation.visitRate(); //获取注解每秒加入桶中的token
        String functionName = method.getName(); // 注解所在方法名区分不同的限流策略

        //获取rateLimiter
        if(map.containsKey(functionName)){
            rateLimiter = map.get(functionName);
        }else {
            // 创建每秒只发出5个令牌对像放在MAP中
            map.put(functionName, RateLimiter.create(limitNum));
            rateLimiter = map.get(functionName);
        }

        try {
            // 尝试获取令牌 rateLimiter.tryAcquire()
            //判断能否在1秒内得到令牌，如果不能则立即返回false，不会阻塞程序
            if (rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
                //执行方法 放行
                return joinPoint.proceed();
            } else {
                //拒绝了请求（服务降级）
                return HttpResult.fail("系统繁忙");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return joinPoint.proceed();
        }
    }
}
