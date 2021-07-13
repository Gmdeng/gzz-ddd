package com.gzz.retail.application.cqrs.goods.command;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CatalogSaveCmd {
    /**
     *  ID id
     */
    private Long id;
    /**
     *  上级ID parentId
     */
    @NotNull(message = "上级分类不能为空")
    private Long parentId;
    /**
     *  名称 name
     */
    @NotEmpty(message = "不能为空")
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
}
