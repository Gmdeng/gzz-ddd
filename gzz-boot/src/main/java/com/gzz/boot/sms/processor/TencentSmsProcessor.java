package com.gzz.boot.sms.processor;

import com.gzz.boot.sms.SmsProcessor;

/**
 * 腾讯
 */
public class TencentSmsProcessor implements SmsProcessor {
    @Override
    public String sendSMS(String msg) {
        System.out.println("腾讯短信……………………………………………………………………………………" + msg);
        return null;
    }

    @Override
    public String sendTemplate(String msg) {
        return null;
    }
}