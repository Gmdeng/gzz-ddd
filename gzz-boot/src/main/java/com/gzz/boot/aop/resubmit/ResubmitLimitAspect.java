package com.gzz.boot.aop.resubmit;

import com.alibaba.fastjson.JSON;
import com.gzz.core.response.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 数据重复提交校验切面 处理
 */
@Slf4j
@Aspect
@Order(3)
public class ResubmitLimitAspect {
    private final static Object PRESENT = new Object();

    @Around("@annotation(com.gzz.boot.aop.resubmit.ResubmitLimit)")
    public Object handleResubmit(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取注解信息
        ResubmitLimit annotation = method.getAnnotation(ResubmitLimit.class);
        int delaySeconds = annotation.delaySeconds();
        Object[] pointArgs = joinPoint.getArgs();
        Parameter[] parameters = method.getParameters();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ipAddr = request.getRemoteAddr();
        log.info("IP : " + request.getRemoteAddr());
        //解析参数
        String keyString = JSON.toJSONString(pointArgs);
        //生成加密参数 使用了content_MD5的加密方式
        String key = ResubmitLimitLock.handleKey(ipAddr + keyString);

        //执行锁
        boolean lock = false;
        try {
            //设置解锁key
            lock = ResubmitLimitLock.getInstance().lock(key, PRESENT);
            if (lock) {
                //放行
                return joinPoint.proceed();
            } else {
                //响应重复提交异常
                return HttpResult.fail("请不要频繁访问");
            }
        } finally {
            //设置解锁key和解锁时间
            ResubmitLimitLock.getInstance().unLock(lock, key, delaySeconds);
        }
    }
}
