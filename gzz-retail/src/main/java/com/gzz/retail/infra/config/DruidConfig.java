package com.gzz.retail.infra.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Order(1)
public class DruidConfig {


    /**
     * *********************************************************************************************
     * 配置Druid的监控
     * <p>
     * *********************************************************************************************
     */

//    1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();

        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123456");
        initParams.put("allow", "");
        //默认就是允许所有访问
        initParams.put("deny", "192.168.15.21");
        bean.setInitParameters(initParams);

//        // IP白名单
//        bean.addInitParameter("allow", "*"); //默认就是允许所有访问
//        // IP黑名单(共同存在时，deny优先于allow)
//        bean.addInitParameter("deny", "192.168.1.100");
//        //控制台管理用户
//        bean.addInitParameter("loginUsername", "admin");
//        bean.addInitParameter("loginPassword", "admin");
//        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
//        bean.addInitParameter("resetEnable", "false");
        return bean;
    }

    //2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter() {
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<WebStatFilter>();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");

        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));

//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setFilter(new WebStatFilter());
////
//        Map<String,String> initParams = new HashMap<>();
//        initParams.put("exclusions","*.js,*.css,/druid/*");
//
//        bean.setInitParameters(initParams);
//
//        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }

}