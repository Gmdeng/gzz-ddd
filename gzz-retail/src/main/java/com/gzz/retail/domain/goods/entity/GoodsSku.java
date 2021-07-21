package com.gzz.retail.domain.goods.entity;

import java.math.BigDecimal;

public class GoodsSku {
    // id
    private Long id;
    // 商品ID
    private Long goodsId;
    // SKU 属性集
    private String attrSet;
    // 缩略图
    private String thumb;
    // 价格
    private BigDecimal price;
    // 价值
    private BigDecimal pv;
    // 销售状态
    private int status;
}
