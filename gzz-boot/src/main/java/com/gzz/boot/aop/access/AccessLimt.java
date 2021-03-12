package com.gzz.boot.aop.access;

import java.lang.annotation.*;

/**
 * 访问频率每秒限流注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface AccessLimt {

}
