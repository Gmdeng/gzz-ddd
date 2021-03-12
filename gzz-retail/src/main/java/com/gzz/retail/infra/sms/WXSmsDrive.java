package com.gzz.retail.infra.sms;

import org.springframework.stereotype.Component;

@Component
public class WXSmsDrive implements SmsDrive {
    @Override
    public String smsType() {
        return "WX";
    }

    @Override
    public void sendSMS() {
        System.out.println("微信短信。。。。。。。。。。。。。。。");
    }
}
