package com.gzz.retail.domain.goods.model;

import lombok.Data;

/**
 * 品牌 - 值对象类(value object)
 */
@Data
public class Brand {
    // ID
    private Long id;
    // 中文名称
    private String cnName;
    // 英文名称
    private String enName;
    // 品牌字母
    private String letter;
    // LOGO图
    private String logo;
    // 备注
    private String notes;

}
