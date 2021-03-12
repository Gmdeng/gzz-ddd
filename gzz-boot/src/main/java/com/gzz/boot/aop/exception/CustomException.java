package com.gzz.boot.aop.exception;

import java.lang.annotation.*;

/**
 * 自定义异常注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface CustomException {
    boolean printError() default false;
    int errorLevel() default 0; // 异常级别
}
