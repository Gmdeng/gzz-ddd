package com.gzz.retail.domain.goods.entity;

import lombok.Data;

/**
 * 品牌 - 值对象类(value object)
 */
@Data
public class Brand {
    /**
     *  ID id
     */
    private Long id;
    /**
     *  中文名称 cnName
     */
    private String cnName;
    /**
     *  英文名称 enName
     */
    private String enName;
    /**
     *  LOGO图标 logo
     */
    private String logo;
    /**
     *  网站 website
     */
    private String website;
    /**
     *  品牌故事 stroy
     */
    private String stroy;
    /**
     *  简介 summary
     */
    private String summary;
}
