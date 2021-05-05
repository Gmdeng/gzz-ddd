package com.demo.config;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;


import com.demo.ds.DatabaseType;
import com.demo.ds.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSourceFactory;


/**
 * springboot集成mybatis的基本入口
 * 1) 获取数据源
 * 2）创建数据源
 * 3）创建SqlSessionFactory
 * 4）配置事务管理器，除非需要使用事务，否则不用配置
 */
// @Configuration
public class MybatisConfig implements EnvironmentAware {

    private Environment environment;

    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    /**
     * 创建数据源,从配置文件中获取数据源信息
     */
    @Bean
    public DataSource testDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", environment.getProperty("jdbc.driverClassName"));
        props.put("url", environment.getProperty("jdbc.url"));
        props.put("username", environment.getProperty("jdbc.username"));
        props.put("password", environment.getProperty("jdbc.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource test1DataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", environment.getProperty("jdbc2.driverClassName"));
        props.put("url", environment.getProperty("jdbc2.url"));
        props.put("username", environment.getProperty("jdbc2.username"));
        props.put("password", environment.getProperty("jdbc2.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**注入数据源
     */
    @Bean
    public DynamicDataSource dataSource(@Qualifier("testDataSource")DataSource testDataSource, @Qualifier("test1DataSource")DataSource test1DataSource) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DatabaseType.mytestdb, testDataSource);
        targetDataSources.put(DatabaseType.mytestdb2, test1DataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(testDataSource);// 默认的datasource设置为myTestDbDataSource

        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("testDataSource") DataSource testDataSource,
                                               @Qualifier("test1DataSource") DataSource test1DataSource) throws Exception{
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(this.dataSource(testDataSource, test1DataSource));
        fb.setTypeAliasesPackage("com.fiberhome.ms.multiDataSource");// 指定基包
        fb.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));//
        return fb.getObject();
    }


    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager testTransactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }


}