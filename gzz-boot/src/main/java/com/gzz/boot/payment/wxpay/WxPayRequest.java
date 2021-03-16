package com.gzz.boot.payment.wxpay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * 微信
 */
public class WxPayRequest extends HashMap<String, String> {

    public WxPayRequest() {
        put("sign_type", "MD5");
    }

    public static WxPayRequest buildH5Pay() {
        WxPayRequest wxPayRequest = new WxPayRequest();
        wxPayRequest.put("trade_type", "MWEB");
        return wxPayRequest;
    }

    /**
     * 公众账号ID
     *
     * @param appid
     * @return
     */
    public WxPayRequest appid(String appid) {
        super.put("appid", appid);
        return this;
    }

    /**
     * 商户号
     *
     * @param mchId
     * @return
     */
    public WxPayRequest mchId(String mchId) {
        super.put("mch_id", mchId);
        return this;
    }

    /**
     * 商户订单号
     *
     * @param tradeNo
     * @return
     */
    public WxPayRequest tradeNo(String tradeNo) {
        super.put("out_trade_no", tradeNo);
        return this;
    }

    /**
     * 货币类型
     *
     * @param feeType
     * @return
     */
    public WxPayRequest feeType(String feeType) {
        super.put("fee_type", feeType);
        return this;
    }

    /**
     * 订单总金额，单位为分
     *
     * @param amount
     * @return
     */
    public WxPayRequest amount(String amount) {
        super.put("total_fee", amount);
        return this;
    }

    /**
     * 终端IP 必须传正确的用户端IP,支持ipv4、ipv6格式
     *
     * @param ipAddr
     * @return
     */
    public WxPayRequest ipAddr(String ipAddr) {
        super.put("spbill_create_ip", ipAddr);
        return this;
    }

    /**
     * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数
     *
     * @param notifyUrl
     * @return
     */
    public WxPayRequest notifyUrl(String notifyUrl) {
        super.put("notify_url", notifyUrl);
        return this;
    }

    /**
     * 场景信息
     *
     * @param sceneInfo
     * @return
     */
    public WxPayRequest sceneInfo(String sceneInfo) {
        super.put("scene_info", sceneInfo);
        return this;
    }

    /**
     * 订单描述
     *
     * @param body
     * @return
     */
    public WxPayRequest body(String body) {
        super.put("body", body);
        return this;
    }

    /**
     * 签名
     *
     * @param sign
     * @return
     */
    public WxPayRequest sign(String sign) {
        super.put("sign", sign);
        return this;
    }

    /**
     * 签名类型
     *
     * @param signType
     * @return
     */
    public WxPayRequest signType(String signType) {
        super.put("sign_type", signType);
        return this;
    }

    /**
     * 附加数据
     *
     * @param attach
     * @return
     */
    public WxPayRequest attach(String attach) {
        super.put("attach", attach);
        return this;
    }

    /**
     * 待签名字符串
     * 每获取一次 nonce_str发生变化
     *
     * @param key 密令
     * @return
     */
    public String toSignatureString(String key) {
        this.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
        Set<String> keySet = this.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if ("sign".equalsIgnoreCase(k)) continue;
            // 参数值为空，则不参与签名
            if (this.get(k) != null && this.get(k).trim().length() > 0) {
                sb.append(k).append("=").append(this.get(k).trim()).append("&");
            }
        }
        sb.append("key=").append(key);
        return sb.toString();
    }

    /**
     * 变成XML字符串
     *
     * @return
     */
    public String toXMLString() {
        Set<String> keySet = this.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (String k : keyArray) {
            sb.append(String.format("<%s>%s</%s>\n", k, this.get(k), k));
        }
        sb.append("</xml>");
        return sb.toString();
    }
}
