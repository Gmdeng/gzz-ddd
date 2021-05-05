package com.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

// @Configuration
// @MapperScan(basePackages ="cn.zhangbox.springboot.dao.teacher",sqlSessionFactoryRef = "teacherSqlSessionFactory")//mybatis接口包扫描
public class DataSourceSlaveConfig {

    @Value("${spring.datasource.slave.type}")
    private Class<? extends DataSource> dataSourceType;

    /**
     *初始化连接池
     * @return
     */
    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /**
     *
     * 构建 SqlSessionFactory
     * @return
     */
    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setTypeAliasesPackage("com.ztzq.data.beans.bigdata");
        bean.setVfs(SpringBootVFS.class);
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/teacher/*.xml"));
        return bean.getObject();
    }

    /**
     * 配置事物
     * @param dataSource
     * @return
     */
    @Bean(name = "slaveTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("slaveDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 构建 SqlSessionTemplate
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "slaveSqlSessionTemplate")
    public SqlSessionTemplate SqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}