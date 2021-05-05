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

    /**
     * 主数据源
     * @return
     */
    @Primary
    @Bean(name = "masterDataSource") // 声明其为Bean实例
    @ConfigurationProperties(prefix = "spring.datasource.db-master")// 读取相关的属性配置
    public DruidDataSource masterDataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setUseLocalSessionState(true);
        return ds;
    }

    /**
     * 从数据源1
     * @return
     */
    @Bean(name = "slave1DS")// 声明其为Bean实例
    @ConfigurationProperties(prefix = "spring.datasource.db-slave") // 读取相关的属性配置
    public DruidDataSource slave1DataSource() {
        DruidDataSource ds = DruidDataSourceBuilder.create().build();
        ds.setUseLocalSessionState(true);
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
    /**
     * 从数据源2
     * @return
     */
    @Bean(name = "slave2DS") // 声明其为Bean实例
    @ConfigurationProperties(prefix = "spring.datasource.db-slave") // 读取相关的属性配置
    public DruidDataSource slave2DataSource() {
        DruidDataSource ds = DruidDataSourceBuilder.create().build();
        return ds;
    }

    /**
     * 动态数据源
     *
     * @param masterDS 主数据源
     * @param slave1DS 从数据源1
     * @param slave2DS 从数据源2
     * @return
     */
    @Bean(name = "dynamicDataSource")
    @ConditionalOnMissingBean(DataSourceRouting.class)
    public DataSourceRouting dynamicDataSource(@Qualifier("masterDataSource") DruidDataSource masterDS,
                                               @Qualifier("slave1DS") DruidDataSource slave1DS,
                                               @Qualifier("slave2DS") DruidDataSource slave2DS) {
        System.out.println(" slave1DataSource======================================" );
        System.out.println(" getUrl DS:          " + slave1DS.getUrl());
        System.out.println(" getMaxWait DS:      " + slave1DS.getMaxWait());
        System.out.println(" getMaxActive DS:    " + slave1DS.getMaxActive());
        System.out.println(" getPassword DS:     " + slave1DS.getPassword());
        System.out.println(" slave2DataSource======================================" );
        System.out.println(" getUrl DS:          " + slave2DS.getUrl());
        System.out.println(" getMaxWait DS:      " + slave2DS.getMaxWait());
        System.out.println(" getMaxActive DS:    " + slave2DS.getMaxActive());
        System.out.println(" getPassword DS:     " + slave2DS.getPassword());
        DataSourceRouting dataSourceRouting = new DataSourceRouting();
        //设置默认数据源
        dataSourceRouting.setDefaultTargetDataSource(masterDS);
        //配置多数据源
        Map<Object, Object> targetDataSource = new HashMap<>(3);
        targetDataSource.put(DataSourceType.MASTER, masterDS);
        targetDataSource.put(DataSourceType.SLAVE1, slave1DS);
        targetDataSource.put(DataSourceType.SLAVE2, slave2DS);

        dataSourceRouting.setTargetDataSources(targetDataSource);
        return dataSourceRouting;
    }
}
