package com.gzz.retail.application.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Application启动后运行,日志打印微服务关键配置信息(服务名,微服务注册中心地址,配置中心)
 */
@Slf4j
@Component
public class AppRunner implements CommandLineRunner {
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(String... strings) {
        Environment env = applicationContext.getEnvironment();
        try {
            log.info("服务名 Application name : {}", env.getProperty("spring.application.name"));
            log.info("微服务注册中心地址 Eureka defaultZone : {}", env.getProperty("eureka.client.service-url.defaultZone"));
            log.info("配置中心 ConfigCenter url : {}", env.getProperty("spring.cloud.config.discovery.service-id"));
            log.info("运行环境 RUNINGTIME EVN : {}", env.getProperty("spring.profiles.active"));
            log.info("运行环境端口  PORT : {}", env.getProperty("server.port"));
            log.info("运行 CONTEXT-PATH : {}", env.getProperty("server.servlet.context-path"));
        } catch (Exception e) {
            log.warn("Get Properties Exception : {}", e.getMessage());
        }
    }
}