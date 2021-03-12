package com.gzz.boot.payment.wxpay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置参数
 */
@Data
@ConfigurationProperties(prefix = "gzz.payment.wxpay")
public class WxPayProperties {
    // API密钥
    private String apiKey;
    // 接口地址
    private String apiUrl;
    // 公众账号ID
    private String appId;
    // 商户号
    private String merchId;
    // 异步通知回调地址
    private String notifyUrl;


    // 场景信息 - 商城名
    private String h5AppName;
    // 场景信息 - 商城网址
    private String h5WapUrl;

}
