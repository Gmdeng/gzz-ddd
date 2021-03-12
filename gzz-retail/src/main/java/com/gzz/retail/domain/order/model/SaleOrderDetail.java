package com.gzz.retail.domain.order.model;

import java.math.BigDecimal;

/**
 * 销售订单明细
 */
public class SaleOrderDetail {
    // 订单编号
    private String orderNo;
    // 商品编码
    private String goodsCode;
    // 商品名称
    private String goodsName;
    // 商品缩图
    private String goodsThumb;
    // 商品ID
    private Long goodsId;
    //
    private Long skuId;
    //
    private Long skuDesc;
    // 数量
    private int qty;
    // 价格
    private BigDecimal price;
    // pv
    private int pv;

}
