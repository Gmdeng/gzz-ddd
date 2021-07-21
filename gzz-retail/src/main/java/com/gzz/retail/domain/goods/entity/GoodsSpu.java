package com.gzz.retail.domain.goods.entity;

import com.gzz.retail.infra.defines.state.SaleStatus;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品
 */
@Data
public class GoodsSpu {
    // id
    private Long id;
    // 分类
    private Catalog catalog;
    // 品牌
    private Brand brand;
    // 商品编码
    private String code;
    // 商品条码
    private String barCode;
    // 商品名称
    private String name;
    // 缩略图
    private String thumb;
    // 商品单位
    private String unit;
    // 价格
    private BigDecimal price;
    // 市场价
    private BigDecimal marketPrice;
    // 价值
    private BigDecimal pv;
    // 销售状态
    private SaleStatus status;
}
