package com.gzz.boot.mybatis.autofill;

import java.lang.annotation.*;
import java.util.Date;

/**
 * 访问日志注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Documented
public @interface AutoFillTime {
    // 指定日期
    String value() default "";
}
