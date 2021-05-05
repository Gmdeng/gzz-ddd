package com.gzz.retail.infra.started;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * Application启动后运行,日志打印微服务关键配置信息(服务名,微服务注册中心地址,配置中心)
 */

@Slf4j
public class TCommandLineRunner implements CommandLineRunner {
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 用于指示bean包含在SpringApplication中时应运行的接口。
     * 可以在同一应用程序上下文中定义多个commandlinerunner bean，并且可以使用有序接口或@order注释对其进行排序。
     * 如果需要访问applicationArguments而不是原始字符串数组，请考虑使用applicationrunner。
     * */
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