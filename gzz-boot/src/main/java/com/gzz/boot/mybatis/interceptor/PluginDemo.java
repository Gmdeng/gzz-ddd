package com.gzz.boot.mybatis.interceptor;

import com.gzz.boot.mybatis.dialect.IDialect;
import com.gzz.core.toolkit.Pager;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Plugin 类其实就是一个代理类，因为它实现了jdk动态代理接口 InvocationHandler
 * 我们核心只需要关注两个方法
 * wrap：
 *      如果看懂了代码案例1的例子，那么这个方法很理解，这个方法就是 mybatis 提供给开发人员使用的一个工具类方法，
 *      目的就是帮助开发人员省略掉 反射解析注解 Intercepts 和 Signature，有兴趣的可以去看看源码 Plugin#getSignatureMap 方法
 *
 * invoke：
 *      这个方法就是根据 wrap 方法的解析结果，判断当前拦截器是否需要进行拦截，
 *      如果需要拦截：将 目标对象+目标方法+目标参数 封装成一个 Invocation 对象，给我们自定义的拦截器 MyInterceptor 的 intercept 方法
 *                   这个时候就刚好对应上了上面案例1中对 intercept 方法的解释了，它就是我们要处理自己逻辑的方法，
 *                   处理好了之后是否需要调用目标对象的方法，比如上面说的 打印了sql语句，是否还要查询数据库呢？答案是肯定的
 *      如果不需要拦截：则直接调用目标对象的方法
 *                   比如直接调用 Executor 的 update 方法进行更新数据库
 *
 */

@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class, CacheKey.class, BoundSql.class }), })
class PluginDemo implements InvocationHandler {
    private volatile IDialect dialect;
    public static Object wrap(Object target, Interceptor interceptor) {
        // 省略
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 省略
        return null;
    }
    public Object invoke(Invocation invocation) throws Throwable {
        try {
            Object[] args = invocation.getArgs();
            // 参数
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = args[1];
            RowBounds rowBounds = (RowBounds) args[2];
            ResultHandler resultHandler = (ResultHandler) args[3];
            CacheKey cacheKey = (CacheKey) args[4];

            // 执行器
            Executor executor = (Executor) invocation.getTarget();
            // sql
            // BoundSql boundSql = (BoundSql) args[5];
            BoundSql boundSql = ms.getBoundSql(parameter);
            // 分页sql
            //String sql = dialect.getPagedSql(boundSql, rowBounds);

            Map<String, Object> params = (Map<String, Object>) parameter;
            // 判断参数中是否有分页
            if (params.containsKey("pageInfo")) {
                Pager pageinfo = (Pager) params.get("pageInfo");
                if (pageinfo.getIndexPage() > 0 && pageinfo.getPageSize() > 0) {
                    RowBounds rb = new RowBounds((pageinfo.getIndexPage() - 1) * pageinfo.getPageSize(), pageinfo.getPageSize());
                    // 使用方言分页
                    String sql = dialect.getPagedSql(boundSql, rb);
                    boundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), parameter);
                    return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, null, boundSql);
                }
            }
            return null;
        } finally {
            if (dialect != null) {
                dialect.afterAll();
            }
        }
    }
}