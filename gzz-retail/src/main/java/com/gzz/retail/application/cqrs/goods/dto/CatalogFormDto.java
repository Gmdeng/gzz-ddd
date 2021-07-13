package com.gzz.retail.application.cqrs.goods.dto;

public class CatalogFormDto {
    /**
     *  ID id
     */
    private Long id;
    /**
     *  上级ID parentId
     */
    private Long parentId;
    /**
     *  名称 name
     */
    private String name;
    /**
     *  缩略图 thumb
     */
    private String thumb;
    /**
     *  排序 idx
     */
    private int idx;
    /**
     *  关键字 keywords
     */
    private String keywords;
    /**
     *  备注 notes
     */
    private String notes;
    /**
     *  是否可用 enable
     */
    private boolean enable;
}
