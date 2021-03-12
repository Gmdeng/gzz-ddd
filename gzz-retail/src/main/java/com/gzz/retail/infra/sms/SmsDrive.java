package com.gzz.retail.infra.sms;

public interface SmsDrive {
    public String smsType();

    public void sendSMS();
}
