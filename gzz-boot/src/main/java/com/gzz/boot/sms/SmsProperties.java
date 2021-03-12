package com.gzz.boot.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置参数
 */
@Data
@ConfigurationProperties(prefix = "gzz.sms")
public class SmsProperties {
    private String clusterName = "elasticsearch";
    private String clusterNodes = "127.0.0.1:9300";
    private String userName = "elastic";
    private String password = "changeme";

}
