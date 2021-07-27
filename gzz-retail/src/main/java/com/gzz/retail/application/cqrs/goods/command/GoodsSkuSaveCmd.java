package com.gzz.retail.application.cqrs.goods.command;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsSkuSaveCmd {
    /**
     *  ID id
     */
    private Long id;
    /**
     *  商品ID goodsId
     */
    private Long goodsId;
    /**
     *  属性集JSON attrsOwn
     */
    private String attrsOwn;
    /**
     *  属性ID用用＿串连 attrsIndexes
     */
    private String attrsIndexes;
    /**
     *  销售价 price
     */
    private BigDecimal price;
    /**
     *  价值 pv
     */
    private BigDecimal pv;
    /**
     *  销售状态 0 status
     */
    private int status;
    /**
     *  缩略图 thumb
     */
    private String thumb;
}
