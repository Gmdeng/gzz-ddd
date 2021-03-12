package com.gzz.boot.mybatis.dynamic;

import com.gzz.boot.mybatis.DataSourceType;

import java.lang.annotation.*;

/**
 * 动态数据源注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicDataSource {
    DataSourceType value() default DataSourceType.MASTER;
}
