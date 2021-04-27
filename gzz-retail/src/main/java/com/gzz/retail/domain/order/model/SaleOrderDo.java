package com.gzz.retail.domain.order.model;

import com.gzz.retail.infra.defines.state.OrderStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * 销售订单
 */
@Data
@Accessors(fluent = true)
public class SaleOrderDo {
    // 编号
    private String no;
    // 时间
    private Date timeOn;
    // 类型
    private int type;
    // 会员编号
    private String memberNo;
    // 会员名称
    private String memberName;
    // 商品总数量
    private int qty;
    // 商品总金额
    private BigDecimal amount;
    // 折扣
    private int discount;
    // 佣金
    private BigDecimal commission;
    // 状态
    private OrderStatus status;
    // 留言
    private String comments;
    // 订单明细
    private Set<SaleOrderDetail> details;
    // 支付方式
    private int modeOfPayment;


    // 打折
    public void doDiscount() {
        //TODO

    }

    // 手续费
    public void doCommission() {

    }
}
