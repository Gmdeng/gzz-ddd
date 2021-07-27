package com.gzz.retail.application.cqrs.goods.command;

import com.gzz.retail.domain.goods.entity.AttributeOption;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AttributeSaveCmd {
    /**
     *  ID id
     */
    private Long id;
    /**
     *  分类ID catalogId
     */
    @NotNull(message = "分类不能为空")
    private Long catalogId;
    /**
     *  属性名称 name
     */
    @NotNull(message = "属性名称不能为空")
    private String name;

    /**
     *  排序 idx
     */
    private int idx;

    /**
     * 属性选项
     */
    @Size(min=1, message = "至少有一个选项值")
    private List<AttributeOption> options;
}
