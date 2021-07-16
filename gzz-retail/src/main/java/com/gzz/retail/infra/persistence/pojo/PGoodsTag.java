package com.gzz.retail.infra.persistence.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 标签 实体类
 *
 * @author Auto-Builder
 * @CrateOn 2020-10-10 11:00:51
 */
@Data
@Accessors
public class PGoodsTag {
    /**
     * ID id
     */
    private Long id;
    /**
     * 中文名称 cnName
     */
    private String cnName;
    /**
     * 英文名称 enName
     */
    private String enName;
    /**
     * LOGO图标 logo
     */
    private String logo;
    /**
     * 网站 website
     */
    private String website;
    /**
     * 品牌故事 stroy
     */
    private String stroy;
}