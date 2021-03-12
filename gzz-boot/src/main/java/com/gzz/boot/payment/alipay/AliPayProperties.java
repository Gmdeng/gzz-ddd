package com.gzz.boot.payment.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置参数
 */
@Data
@ConfigurationProperties(prefix = "gzz.payment.alipay")
public class AliPayProperties {
   // 请求网关地址
   private String apiUrl;
   // 商户appid
   private String appId;
   // 页面跳转同步通知页面路径
   private String returnUrl;
   // 服务器异步通知页面路径
   private String notifyUrl;
}
