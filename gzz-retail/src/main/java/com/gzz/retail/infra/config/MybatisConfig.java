package com.gzz.retail.infra.config;

import com.gzz.boot.mybatis.DataSourceInterceptor;
import com.gzz.boot.mybatis.DataSourceRouting;
import com.gzz.boot.mybatis.PaginationInterceptor;
import com.gzz.boot.mybatis.autofill.AutoFillInterceptor;
import com.gzz.boot.mybatis.cache.RedisCacheTransfer;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

/**
 * mybatis 注解版
 */
@Configuration
@AutoConfigureAfter({DruidConfig.class, RedisCacheConfig.class})
public class MybatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true); //设置驼峰命名规则
                /**
                // 这个配置使全局的映射器启用或禁用缓存
                configuration.setCacheEnabled(true);
                // 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载。系统默认值是true
                // 查询时，关闭关联对象即时加载以提高性能
                configuration.setLazyLoadingEnabled(false);
                // 当启用时，有延迟加载属性的对象在被调用时将会完全加载任意属性。否则，每种属性将会按需要加载。
                // 设置关联对象加载的形态，此处为按需加载字段(加载字段由SQL指 定)，不会加载关联表的所有字段，以提高性能
                configuration.setAggressiveLazyLoading(false);
                // 对于未知的SQL查询，允许返回不同的结果集以达到通用的效果
                // 允许或不允许多种结果集从一个单独的语句中返回（需要适合的驱动）。 系统默认值是true
                configuration.setMultipleResultSetsEnabled(true);
                // 允许使用列标签代替列名
                configuration.setUseColumnLabel(true);
                // 给予被嵌套的resultMap以字段-属性的映射支持 FULL,PARTIAL
                configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
                // 对于批量更新操作缓存SQL以提高性能 BATCH,SIMPLE
                configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
                // 设置超时时间，它决定驱动等待一个数据库响应的时间。
                // 数据库超过25000秒仍未响应则超时
                configuration.setDefaultStatementTimeout(25000);
                // 不允许使用自定义的主键值(比如由程序生成的UUID 32位编码作为键值)，数据表的PK生成策略将被覆盖
                // 允许 JDBC 支持生成的键。需要适合的驱动。如果设置为 true 则这个设置强制生成的键被使用
                // 尽管一些驱动拒绝兼容但仍然有效（比如 Derby）
                configuration.setUseGeneratedKeys(false);
                // 数据库 下划线字段和 java 实体 bean 的驼峰标识相互转化
                configuration.setMapUnderscoreToCamelCase(true);
                */
                // 打印查询语句
                // SLF4J | LOG4J | LOG4J2 | JDK_LOGGING | COMMONS_LOGGING | STDOUT_LOGGING |
                // NO_LOGGING，可以根据自己的需要进行配置
                // org.apache.ibatis.logging.stdout.StdOutImpl
                // configuration.setLogImpl(StdOutImpl.class);
                // <!-- 配置默认的执行器。SIMPLE 执行器没有什么特别之处。REUSE 执行器重用预处理语句。BATCH 执行器重用语句和批量更新
                // <setting name="defaultExecutorType" value="REUSE" />
                // -->
                /**
                // Specifies which Object's methods trigger a lazy load
                Set<String> triggerMethods = new HashSet<>();
                triggerMethods.add("equals");
                triggerMethods.add("clone");
                triggerMethods.add("hashCode");
                triggerMethods.add("toString");
                configuration.setLazyLoadTriggerMethods(triggerMethods);
                // Allows using RowBounds on nested statements -->
                configuration.setSafeRowBoundsEnabled(false);
                // MyBatis uses local cache to prevent circular references and speed up repeated
                // nested queries
                // . By default (SESSION) all queries executed during a session are cached. If
                // localCacheScope=STATEMENT
                // local session will be used just for statement execution,
                // no data will be shared between two different calls to the same SqlSession.
                configuration.setLocalCacheScope(LocalCacheScope.SESSION);
                // Specifies the JDBC type for null values when no specific JDBC type was
                // provided for the parameter.
                // Some drivers require specifying the column JDBC type but others work with
                // generic values
                // like NULL, VARCHAR or OTHER. -->
                configuration.setJdbcTypeForNull(JdbcType.OTHER);

                */
            }
        };
    }

    /**
     * 给MyBatis缓存传参
     *
     * @return
     */
    @Bean
    public RedisCacheTransfer redisCacheTransfer(RedisTemplate<String, Object> redisTemplate){
        return new RedisCacheTransfer(redisTemplate);
    }

    /**
     * Session工厂
     * @param dynamicDataSource
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SqlSessionFactory.class)
    public SqlSessionFactory sqlSessionFactory(DataSourceRouting dynamicDataSource) {
        //
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        //bean.setConfiguration(configurationCustomizer);
        //bean.setTypeAliasesPackage("com.gzz.api.dao.pojo");

        // 分页、数据源切换
        Properties pageProp = new Properties();
        pageProp.setProperty("dialect", "mysql");
        pageProp.setProperty("pageSqlId", "mysql10001");
        PaginationInterceptor pagerInterceptor = new PaginationInterceptor();
        pagerInterceptor.setProperties(pageProp);
        //
        Interceptor[] plugins = { pagerInterceptor, new AutoFillInterceptor(), new DataSourceInterceptor() };
        bean.setPlugins(plugins);
        try {
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 数据库事务
     * @param dynamicDataSource
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
    public PlatformTransactionManager platformTransactionManager(
            @Qualifier("dynamicDataSource") DataSourceRouting dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

}
