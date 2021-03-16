package com.gzz.boot.payment;

import com.gzz.boot.payment.alipay.AliPayProcessor;
import com.gzz.boot.payment.alipay.AliPayProperties;
import com.gzz.boot.payment.wxpay.WxPayProcessor;
import com.gzz.boot.payment.wxpay.WxPayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@EnableConfigurationProperties({WxPayProperties.class, AliPayProperties.class})
public class PaymentConfiguration {

    /**
     * 微信
     *
     * @return
     */
    @Bean
    public WxPayProcessor wxPaymentProcessor() {
        return new WxPayProcessor();
    }

    /**
     * 支付宝
     *
     * @return
     */
    @Bean
    public AliPayProcessor aliPayProcessor() {
        return new AliPayProcessor();
    }
}
