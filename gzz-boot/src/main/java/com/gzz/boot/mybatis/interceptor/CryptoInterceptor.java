package com.gzz.boot.mybatis.interceptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({
        @Signature(
                type= Executor.class,
                method = "update",
                args = {MappedStatement.class,Object.class}),
        @Signature(
                type= Executor.class,
                method = "query",
                args = {MappedStatement.class,Object.class, RowBounds.class,ResultHandler.class})
})
public class CryptoInterceptor implements Interceptor{

    public Object intercept(Invocation invocation) throws Throwable {

        final Executor executor = (Executor)invocation.getTarget();
        final Method method = (Method) invocation.getMethod();
        final MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        final Object parameterObject = (Object) invocation.getArgs()[1];

        if(method.getName().equals("update")){
            /* encrypt security fields of parameterObject */
            //encrypt(parameterObject);
            return invocation.proceed();
        }
        else if(method.getName().equals("query")){
            final RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
            final BoundSql boundSql = (BoundSql) mappedStatement.getBoundSql(parameterObject);

            CacheKey cacheKey = executor.createCacheKey(mappedStatement, parameterObject, rowBounds, boundSql);
            boolean isCached = executor.isCached(mappedStatement, cacheKey);
            if(isCached) return invocation.proceed();
            else{
                /* encrypt security fields of parameterObject */
                //encrypt(parameterObject);
                List<?> objectList = (List<?>)invocation.proceed();
                /* decrypt security fields of element from objectList */
                //decrypt(objectList);
                return objectList;
            }
        }
        else{
            throw new RuntimeException("unexpected method intercepted: "+method.getName());
        }
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }

}