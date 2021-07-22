package com.gzz.retail.domain.goods.entity;

import lombok.Data;

/**
 * 销售属性
 */
@Data
public class Attribute {
    private Long id;
    // 所有者id(catalogId/goodsId)
    private Long ownerId;
    // 属性名称
    private String name;
    // 属性值项
    private String[] values;
}
