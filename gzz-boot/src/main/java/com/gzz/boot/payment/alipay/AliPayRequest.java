package com.gzz.boot.payment.alipay;

import com.gzz.boot.payment.wxpay.WxPayRequest;

import java.util.HashMap;

public class AliPayRequest extends HashMap<String, String> {


    public static AliPayRequest buildH5Pay() {
        AliPayRequest aliPayRequest = new AliPayRequest();
        aliPayRequest.put("method", "alipay.trade.page.pay");
        return aliPayRequest;
    }
    public AliPayRequest(){
        put("sign_type", "RSA32");
        put("charset", "UTF-8");
        put("format", "JSON");
        put("version", "1.0");
        put("product_code","FAST_INSTANT_TRADE_PAY");
    }

    /**
     * 商户appid
     * @param appid
     * @return
     */
    public AliPayRequest appid(String appid){
        super.put("appid", appid);
        return this;
    }

    /**
     * 商户订单号
     * @param tradeNo
     * @return
     */
    public AliPayRequest tradeNo(String tradeNo){
        super.put("out_trade_no", tradeNo);
        return this;
    }
    /**
     * 订单总金额，单位为分
     * @param amount
     * @return
     */
    public AliPayRequest amount(String amount){
        super.put("total_amount", amount);
        return this;
    }

    /**
     * 订单标题
     * @param subject
     * @return
     */
    public AliPayRequest subject(String subject){
        super.put("subject", subject);
        return this;
    }
    /**
     * 签名
     * @param sign
     * @return
     */
    public AliPayRequest sign(String sign){
        super.put("sign", sign);
        return this;
    }

    /**
     * 支付后同步通知回调地址
     * @param returnUrl
     * @return
     */
    public AliPayRequest returnUrl(String returnUrl){
        super.put("return_url", returnUrl);
        return this;
    }
    /**
     * 接收支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数
     * @param notifyUrl
     * @return
     */
    public AliPayRequest notifyUrl(String notifyUrl){
        super.put("notify_url", notifyUrl);
        return this;
    }
}
