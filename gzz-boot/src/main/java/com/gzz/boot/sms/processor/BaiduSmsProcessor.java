package com.gzz.boot.sms.processor;

import com.gzz.boot.sms.SmsProcessor;

/**
 * 百度
 */
public class BaiduSmsProcessor implements SmsProcessor {
    @Override
    public String sendSMS(String msg) {
        System.out.println("百度短信。。。。。。" + msg);
        return null;
    }

    @Override
    public String sendTemplate(String msg) {
        return null;
    }
}