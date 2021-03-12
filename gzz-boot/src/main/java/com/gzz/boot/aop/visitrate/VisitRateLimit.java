package com.gzz.boot.aop.visitrate;

import java.lang.annotation.*;

/**
 * 访问频率每秒限流注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface VisitRateLimit {
    /**
     * 默认每秒限流10个
     * @return Time unit is  one second
     */
    int visitRate() default 20;
}
