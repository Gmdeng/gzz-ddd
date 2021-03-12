package com.gzz.retail.domain.order.model;

import com.gzz.retail.infra.defines.state.ReturnStatus;

/**
 * 退货单
 */
public class ReturnOrder {
    // 编号
    private String no;
    // 时间
    private String timeOn;
    // 订单编号
    private String orderNo;
    private ReturnStatus status;
}
