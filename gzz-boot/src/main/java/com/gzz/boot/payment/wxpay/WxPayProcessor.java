package com.gzz.boot.payment.wxpay;

import com.gzz.core.util.EncryptUtil;
import com.gzz.core.util.HttpClientUtil;
import com.gzz.core.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 */
@Slf4j
public class WxPayProcessor {
    @Resource
    private WxPayProperties properties;

    /**
     * 统一支付
     *
     * @param pay
     * @return
     */
    public String unifiedOrder(WxPayRequest pay) {
        pay.appid(properties.getAppId())
                .mchId(properties.getMerchId())
                .body("商品描述-H5")
                .sceneInfo(sceneH5Info());
        String signatureString = pay.toSignatureString(properties.getApiKey());


        log.info("订单[{}]待签串：{}", pay.get("out_trade_no"), signatureString);
        String sign = EncryptUtil.MD5Encode(signatureString);
        log.info("订单[{}]签名串：{}", pay.get("out_trade_no"), sign);
        String xml = pay.sign(sign).toXMLString();
        log.info("订单[{}]发送内容：{}", pay.get("out_trade_no"), xml);
        // 发送请求
        String returnData = HttpClientUtil.doPost(properties.getApiUrl(), xml);
        try {
            Map<String, String> retMap = XmlUtil.xmlToMap(returnData);
            log.error("订单[{}]响应内容return_code：{}", pay.get("out_trade_no"), retMap.get("return_code"));
            log.error("订单[{}]响应内容return_msg：{}", pay.get("out_trade_no"), retMap.get("return_msg"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return xml;
    }

    /**
     * H5应用场景
     *
     * @return
     */
    private String sceneH5Info() {
        return "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"" + properties.getH5WapUrl() + "\",\"wap_name\": \"" + properties.getH5AppName() + "\"}}";
    }
}
