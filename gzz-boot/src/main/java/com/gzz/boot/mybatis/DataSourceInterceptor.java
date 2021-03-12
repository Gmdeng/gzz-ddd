package com.gzz.boot.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;

/**
 * 数据源切换拦截器
 *
 * @author G-m
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class DataSourceInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation inv) throws Throwable {
        log.debug("进入了注解 DataSourceInterceptor。。。。。。。。。。。。");
        boolean sync = TransactionSynchronizationManager.isSynchronizationActive();
        if (sync == false) {
            Object[] args = inv.getArgs();
            MappedStatement mappedStatement = (MappedStatement) args[0];
            String msId = mappedStatement.getId();
            BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(args[1]);
            String sql = boundSql.getSql().toLowerCase(Locale.CHINA);

            // 读方法, !selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
            if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)
                    && msId.contains(SelectKeyGenerator.SELECT_KEY_SUFFIX) == false) {
                log.info("使用=======>从服务器");
                DataSourceContextHolder.Slave();
            } else {
                log.info("使用=======>主服务器");
                DataSourceContextHolder.Master();
            }
            log.info("设置方法[{}]", msId);
            log.info("使用数据源 [{}] Strategy", DataSourceContextHolder.getDataSourceKey().name());
            log.info("SqlCommandType [{}]", mappedStatement.getSqlCommandType().name());
            log.info("执行SQL [{}]", sql);
        }
        return inv.proceed();
    }

}
