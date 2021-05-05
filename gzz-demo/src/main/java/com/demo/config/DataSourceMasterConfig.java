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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
// @MapperScan(basePackages ="cn.zhangbox.springboot.dao.student",sqlSessionFactoryRef = "studentSqlSessionFactory")//mybatis接口包扫描
public class DataSourceMasterConfig {

    @Value("${spring.datasource.master.type}")
    private Class<? extends DataSource> dataSourceType;

    /**
     *初始化连接池
     * @return
     */
    @Primary
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        System.out.printf("===============================================");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /**
     *
     * 构建 SqlSessionFactory
     * @return
     */
    @Primary
    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setTypeAliasesPackage("com.ztzq.data.beans.bigdata");
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeHandlersPackage("com.demo.dao.handlers");
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/student/*.xml"));
        return bean.getObject();
    }

    /**
     * 配置事物
     * @param dataSource
     * @return
     */
    @Primary
    @Bean(name = "masterTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("masterDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 构建 SqlSessionTemplate
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Primary
    @Bean(name = "masterSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}