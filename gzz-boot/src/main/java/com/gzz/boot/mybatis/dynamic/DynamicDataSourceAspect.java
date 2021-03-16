package com.gzz.boot.mybatis.dynamic;

import com.gzz.boot.mybatis.DataSourceContextHolder;
import com.gzz.boot.mybatis.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * AOP动态切换数据源
 * 使用AOP，以自定义注解注解在的方法为切点，动态切换数据源
 */
@Slf4j
@Aspect
//@Component
public class DynamicDataSourceAspect {
    @Before("@annotation(com.gzz.boot.mybatis.dynamic.DynamicDataSource))")
    public void switchDataSource(JoinPoint joinPoint, DynamicDataSource dataSource) {
        DataSourceContextHolder.setDataSourceKey(dataSource.value());
    }

    @After("@annotation(com.gzz.boot.mybatis.dynamic.DynamicDataSource))")
    public void restoreDataSource(JoinPoint joinPoint, DynamicDataSource dataSource) {
        DataSourceContextHolder.setDataSourceKey(DataSourceType.MASTER);

    }

}