package com.gzz.retail.infra.sms;

import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class AliSmsDrive implements SmsDrive {
    @Override
    public String smsType() {
        return "ALI";
    }

    @Override
    public void sendSMS() {
        System.out.println("阿里短信。。。。。");
    }
}
