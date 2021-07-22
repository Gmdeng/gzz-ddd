package com.gzz.retail.domain.goods.entity;

import lombok.Data;

/**
 * 销售属性
 */
@Data
public class Attribute {
    /**
     *  ID id
     */
    private Long id;
    /**
     *  分类ID catalogId
     */
    private Long catalogId;
    /**
     *  属性名称 name
     */
    private String name;
    /**
     *  属性值项，用逗号隔里 values
     */
    private String values;
    /**
     *  排序 idx
     */
    private int idx;

    private List<>
}
