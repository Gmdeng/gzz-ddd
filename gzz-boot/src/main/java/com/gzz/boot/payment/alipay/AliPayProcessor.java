package com.gzz.boot.payment.alipay;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

@Slf4j
public class AliPayProcessor {
    @Resource
    private AliPayProperties properties;

    /**
     * alipay.trade.wap.pay	手机网页支付接口	通过此接口传入订单参数，同时唤起支付宝手机网页支付页面
     * alipay.trade.close
     * <p>
     * 交易关闭接口
     * 通过此接口关闭此前已创建的交易，关闭后，用户将无法继续付款。仅能关闭创建后未支付的交易。
     * alipay.trade.query
     * <p>
     * 交易状态查询接口
     * 通过此接口查询某笔交易的状态，交易状态：交易创建，等待买家付款；未付款交易超时关闭，或支付完成后全额退款；交易支付成功；交易结束，不可退款。
     * alipay.trade.refund
     * <p>
     * 交易退款接口
     * 通过此接口对单笔交易进行退款操作。
     * alipay.trade.fastpay.refund.query
     * <p>
     * 退款查询
     * 查询退款订单的状态。
     * alipay.data.dataservice.bill.downloadurl.query
     * <p>
     * 账单查询接口
     * 调用此接口获取账单的下载链
     *
     * @param request
     * @return
     */
    public String unifiedOrder(AliPayRequest request) {

        return null;
    }
}
