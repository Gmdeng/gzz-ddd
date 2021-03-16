package com.gzz.boot.sms;

import com.gzz.boot.sms.processor.AliyunSmsProcessor;
import com.gzz.boot.sms.processor.BaiduSmsProcessor;
import com.gzz.boot.sms.processor.TencentSmsProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;

/**
 * 短信配置信息
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {
    @Resource
    private SmsProperties properties;

    /**
     * @return
     */
    //判断属性是否等于true
    @ConditionalOnProperty(prefix = "gzz.sms", name = "type", havingValue = "aliyun")
    @Bean
    @Primary
    public AliyunSmsProcessor aliyunSmsProcessor() {
        log.info("=======" + properties.getClusterName());
        log.info("=======" + properties.getClusterNodes());
        log.info("=======" + properties.getUserName());
        log.info("=======" + properties.getPassword());
        return new AliyunSmsProcessor();
    }

    /**
     * @return
     */
    @ConditionalOnProperty(prefix = "gzz.sms", name = "type", havingValue = "baidu")
    @Bean
    public BaiduSmsProcessor baiduSmsProcessor() {
        return new BaiduSmsProcessor();
    }

    /**
     * @return
     */
    @ConditionalOnProperty(prefix = "gzz.sms", name = "type", havingValue = "tencent")
    @Bean
    public TencentSmsProcessor TencentSmsProcessor() {
        return new TencentSmsProcessor();
    }
}
