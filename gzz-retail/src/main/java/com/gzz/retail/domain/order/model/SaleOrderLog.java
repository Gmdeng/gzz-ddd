package com.gzz.retail.domain.order.model;

import com.gzz.retail.infra.defines.state.OrderStatus;

import java.util.Date;

public class SaleOrderLog {
    // 流水ID
    private long id;
    // 订单号
    private String orderNo;
    // 订单状态
    private OrderStatus status;
    // 更变时间
    private Date occurredOn;
}
