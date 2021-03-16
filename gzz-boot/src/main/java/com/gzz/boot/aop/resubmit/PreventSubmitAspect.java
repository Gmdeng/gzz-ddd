package com.gzz.boot.aop.resubmit;

import com.gzz.core.exception.BizzException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 防重复提交
 */
//@Component
//@Aspect
@Slf4j
public class PreventSubmitAspect {
    /**
     * 放重redis前缀
     */
    private static String API_PREVENT_SUBMIT = "api:preventSubmit:";

    /**
     * 放重分布式锁前缀
     */
    private static String API_LOCK_PREVENT_SUBMIT = "api:preventSubmit:lock:";

    /**
     * 失效时间
     */
    private static Integer INVALID_NUMBER = 3;

    /**
     * redis
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 防重
     */
    @Around("@annotation(com.qianxian.user.annotation.PreventSubmit)")
    public Object preventSubmitAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        // RLock lock = null;

        try {

            //获取目标方法的参数
            Object[] args = joinPoint.getArgs();

            //获取当前request请求
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

            //获取请求地址
            String requestUri = request.getRequestURI();

            //获取用户ID
            Long userId = null;
//            try {
//                userId = TokenUtil.getUserId(request);
//            }catch (Exception e){}

            //拼接锁前缀，采用同一方法，同一用户,同一接口
            String temp = requestUri.concat(Arrays.asList(args).toString()) + (userId != null ? userId : "");
            temp = temp.replaceAll("/", "");

            //拼接rediskey
            String lockPrefix = API_LOCK_PREVENT_SUBMIT.concat(temp);
            String redisPrefix = API_PREVENT_SUBMIT.concat(temp);

            /**
             * 对同一方法同一用户同一参数加锁,即使获取不到用户ID,每个用户请求数据也会不一致，不会造成接口堵塞
             */
//            lock = this.redissonClient.getLock(lockPrefix);
//            lock.lock();

            String flag = this.stringRedisTemplate.opsForValue().get(redisPrefix);
            if (StringUtils.isNotEmpty(flag)) {
                throw new BizzException("您当前的操作太频繁了,请稍后再试!");
            }

            //存入redis,设置失效时间
            this.stringRedisTemplate.opsForValue().set(redisPrefix, redisPrefix, INVALID_NUMBER, TimeUnit.SECONDS);

            //执行目标方法
            Object result = joinPoint.proceed(args);
            return result;

        } finally {
//            if(lock != null){
//                lock.unlock();
//            }
        }

    }

}