package com.gzz.boot.aop.log;

import java.lang.annotation.*;

/**
 * 访问日志注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface VisitLog {
    // 是否开启详细日志
    boolean open() default true;

    // 是否输出响应
    boolean logResponse() default true;

    // 摘要信息
    String digest() default "";

    // 是否仅打印外层信息
    boolean onlyOutermostPrint() default true;
}
