package com.gzz.boot.mybatis.autofill;

import java.lang.annotation.*;

/**
 * 访问日志注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Documented
public @interface AutoFillUser {
    // 指定用户
    String value() default "";
}
