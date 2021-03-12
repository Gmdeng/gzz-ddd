package com.gzz.boot.sms.processor;

import com.gzz.boot.sms.SmsProcessor;

/**
 * 阿里云
 */

public class AliyunSmsProcessor implements SmsProcessor {
    @Override
    public String sendSMS(String msg) {
        System.out.println("阿里短信&&&&&&&&&&&&" + msg);
        return null;
    }

    @Override
    public String sendTemplate(String msg) {
        return null;
    }
}
