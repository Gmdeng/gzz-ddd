package com.gzz.retail.application.cqrs.goods.command;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsSpuSaveCmd {
    /**
     *  id id
     */
    private Long id;
    /**
     *  分类ID catalogId
     */
    private Long catalogId;
    /**
     *  商品名称 name
     */
    private String name;
    /**
     *  商品编码 code
     */
    private String code;
    /**
     *  商品条码 barCode
     */
    private String barCode;
    /**
     *  品牌ID brandId
     */
    private Long brandId;
    /**
     *  缩略图 thumb
     */
    private String thumb;
    /**
     *  单位 unit
     */
    private String unit;
    /**
     *  规格参数JSON specsOwn
     */
    private String specsOwn;
    /**
     *  排序 idx
     */
    private int idx;
    /**
     *  销售价 price
     */
    private BigDecimal price;
    /**
     *  价值  pv
     */
    private BigDecimal pv;
    /**
     *  销售状态 status
     */
    private int status;
    /**
     *  商品加权平均成本 averageCost
     */
    private BigDecimal averageCost;
    /**
     *  运费 freight
     */
    private BigDecimal freight;
}
