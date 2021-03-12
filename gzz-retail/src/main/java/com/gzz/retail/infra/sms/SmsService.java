package com.gzz.retail.infra.sms;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class SmsService {
    public static Map<String,SmsDrive> currMap = Maps.newHashMap();
    public SmsService(List<SmsDrive> drives){
        drives.forEach(d-> currMap.put(d.smsType(), d));
//        for (SmsDrive d: drives) {
//            currMap.put(d.smsType(), d);
//        }
    }

    public void sendSMS(String type){
        SmsDrive sms = currMap.get(type);
        sms.sendSMS();
    }
}
