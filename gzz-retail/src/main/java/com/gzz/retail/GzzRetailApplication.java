package com.gzz.retail;

import com.gzz.boot.AutoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

/**
 *
 */
@EnableAsync  // 开启
@SpringBootApplication
@ImportAutoConfiguration(AutoConfig.class)
@ServletComponentScan //(basePackages = "com.gzz.retail.facade.servlet") // 扫描servlet组件所在的包
public class GzzRetailApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(GzzRetailApplication.class, args);
    }

//    @Bean
//    public ApplicationRunner applicationRunner() throws InterruptedException {
//        System.out.println("===================模拟延迟--------------------");
//        Thread.sleep(30000);
//        return args -> {
//            System.out.println("===================模拟延迟启动--------------------");
//        };
//    }
}
