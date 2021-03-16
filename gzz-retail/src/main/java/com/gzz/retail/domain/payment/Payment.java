package com.gzz.retail.domain.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {
    // 编号
    private String no;
    // 时间
    private String timeOn;
    // 订单编号
    private String orderNo;
    // 金额
    private BigDecimal amount;
    // 手续费
    private BigDecimal fee;
    // 响应代码
    private String respCode;
    // 响应信息
    private String respMessage;
    // 响应时间
    private String respTimeOn;
    // 响应交易单号
    private String respTradeNo;
    // 状态
    private PaymentStatus status;

}
