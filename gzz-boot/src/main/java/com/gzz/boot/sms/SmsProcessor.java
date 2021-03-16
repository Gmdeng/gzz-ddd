package com.gzz.boot.sms;

/**
 *
 */
public interface SmsProcessor {
    /**
     * 发送短信
     *
     * @param msg
     * @return
     */
    public String sendSMS(String msg);

    /**
     * 发送模版短信
     *
     * @param msg
     * @return
     */
    public String sendTemplate(String msg);
}
