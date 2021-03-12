package com.gzz.boot.aop.resubmit;

import java.lang.annotation.*;

/**
 * 防重复提交
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResubmitLimit {
    /**
     * 延时时间 在延时多久后可以再次提交
     * @return Time unit is  one second
     */
    int delaySeconds() default 20;
}
