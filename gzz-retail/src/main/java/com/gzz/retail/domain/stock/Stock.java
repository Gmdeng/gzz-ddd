package com.gzz.retail.domain.stock;

import lombok.Data;

/**
 * 库存
 */
@Data
public class Stock {
    // ID
    private Long id;
    // 商品ID
    private Long goodsId;
    // 商品SKUID
    private Long skuId;
    // 可用数量
    private int availQty;
    // 锁定数量
    private int lockQty;
    // 锁定数量
    private int frozenQty;
    // 耗损数量
    private int wasteQty;
    // 最低库存量
    private int lowQty;
}
