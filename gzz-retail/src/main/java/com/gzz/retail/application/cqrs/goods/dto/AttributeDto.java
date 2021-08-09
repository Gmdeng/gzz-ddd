package com.gzz.retail.application.cqrs.goods.dto;

import com.gzz.retail.domain.goods.entity.AttributeOption;
import lombok.Data;
import java.util.List;

/**
 *
 */
@Data
public class AttributeDto {
    /**
     *  ID id
     */
    private Long id;
    /**
     *  分类ID catalogId
     */
    private Long catalogId;
    /**
     *  分类名称 catalogName
     */
    private String catalogName;
    /**
     *  属性名称 name
     */
    private String name;

    /**
     *  排序 idx
     */
    private int idx;

    /**
     * 属性选项
     */
    private List<AttributeOption> options;
}
