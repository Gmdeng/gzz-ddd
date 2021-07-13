package com.gzz.retail.domain.goods.entity;

import lombok.Data;

/**
 * 分类
 * category
 */
@Data
public class Catalog {
    // ID
    private Long id;
    // 上级分类
    private Catalog parent;
    // 名称
    private String name;
    // 排序
    private int idx;
    // 备注
    private String notes;
}
