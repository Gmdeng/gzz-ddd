package com.gzz.retail.infra.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.gzz.boot.mybatis.DataSourceRouting;
import com.gzz.boot.mybatis.DataSourceType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据源
 */
@Configuration
@AutoConfigureAfter(DruidConfig.class)
@EnableAutoConfiguration  // 启用事务
public class DataSourceConfig {
    @Primary
    @Bean(name = "masterDataSource") // 声明其为Bean实例
    // 读取相关的属性配置
    @ConfigurationProperties(prefix = "spring.datasource.db-master")
    public DruidDataSource masterDataSource() {
        DruidDataSource ds = new DruidDataSource();
        return ds;
    }

    @Bean(name = "slave1DataSource")// 声明其为Bean实例
    @ConfigurationProperties(prefix = "spring.datasource.db-slave") // 读取相关的属性配置
    public DruidDataSource slave1DataSource() {
        DruidDataSource ds = DruidDataSourceBuilder.create().build();
        /*
        ds.setMaxActive(5);
        ds.setMaxWait(100l);
        ds.setInitialSize(2);
        ds.setMinIdle(2);
        ds.setLogAbandoned(true);
        ds.setPoolPreparedStatements(true);
        ds.setMaxPoolPreparedStatementPerConnectionSize(20);*/
        return ds;
    }

    @Bean(name = "slave2DataSource") // 声明其为Bean实例
    @ConfigurationProperties(prefix = "spring.datasource.db-slave") // 读取相关的属性配置
    public DruidDataSource slave2DataSource() {
        DruidDataSource ds = DruidDataSourceBuilder.create().build();
        return ds;
    }

    /**
     * 动态数据源
     *
     * @param masterDataSource
     * @param slave1DataSource
     * @param slave2DataSource
     * @return
     */
    @Bean(name = "dynamicDataSource")
    @ConditionalOnMissingBean(DataSourceRouting.class)
    public DataSourceRouting dynamicDataSource(@Qualifier("masterDataSource") DruidDataSource masterDataSource,
                                               @Qualifier("slave1DataSource") DruidDataSource slave1DataSource,
                                               @Qualifier("slave2DataSource") DruidDataSource slave2DataSource) {
        System.out.println(" getUrl DS: " + slave2DataSource.getUrl());
        System.out.println(" getMaxWait DS: " + slave2DataSource.getMaxWait());
        System.out.println(" getMaxActive DS: " + slave2DataSource.getMaxActive());
        System.out.println(" getPassword DS: " + slave2DataSource.getPassword());
        DataSourceRouting dataSourceRouting = new DataSourceRouting();
        //设置默认数据源
        dataSourceRouting.setDefaultTargetDataSource(masterDataSource);
        //配置多数据源
        Map<Object, Object> targetDataSource = new HashMap<>(3);
        targetDataSource.put(DataSourceType.MASTER, masterDataSource);
        targetDataSource.put(DataSourceType.SLAVE1, slave1DataSource);
        targetDataSource.put(DataSourceType.SLAVE2, slave2DataSource);

        dataSourceRouting.setTargetDataSources(targetDataSource);
        return dataSourceRouting;
    }
}
