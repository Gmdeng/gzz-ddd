package com.gzz.retail.infra.sms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

//
@ActiveProfiles("remote")
public class TestSms {
    @Autowired
    SmsService smsService;

    @Test
    public void testSend() {
        // smsService.sendSMS("ALI");
        int stat = 1;
        stat <<= 10;   // 先用JAVA的位运算将条件算好
        int end = 1024;
        end >>= 1;
        System.out.println(stat);
        System.out.println(end);
    }
}
