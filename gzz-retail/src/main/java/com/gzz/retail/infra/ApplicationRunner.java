package com.gzz.retail.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * Application启动后运行,日志打印微服务关键配置信息(服务名,微服务注册中心地址,配置中心)
 */

@Slf4j
public class ApplicationRunner implements CommandLineRunner {
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(String... strings) {
        Environment environment = applicationContext.getEnvironment();
        try {
            log.info("服务名 Application name : {}", environment.getProperty("spring.application.name"));
            log.info("微服务注册中心地址 Eureka defaultZone : {}", environment.getProperty("eureka.client.service-url.defaultZone"));
            log.info("配置中心 ConfigCenter url : {}", environment.getProperty("spring.cloud.config.discovery.service-id"));
        } catch (Exception e) {
            log.warn("Get Properties Exception : {}", e.getMessage());
        }
    }
}