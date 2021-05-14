package com.gzz.boot.sms;

/**
 *  邮件发送
 *
 */
public class SmsTemplate {
    private SmsProcessor smsProcessor;

    public SmsTemplate(SmsProcessor smsProcessor) {
        this.smsProcessor = smsProcessor;
    }

    /**
     * 发送短信
     *
     * @param msg
     * @return
     */
    public String sendSMS(String msg) {
        return this.smsProcessor.sendSMS(msg);
    }

    /**
     * 发送模版短信
     *
     * @param msg
     * @return
     */
    public String sendTemplate(String msg) {

        return this.smsProcessor.sendTemplate(msg);
    }

}
