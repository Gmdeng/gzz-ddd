package com.gzz.retail.application.system.command;

import com.gzz.retail.infra.defines.types.OperateType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class ModuleSaveCmd {
    /**
     * 模块ID
     */
    private Long id;
    /**
     * 父模块ID parentId
     */
    @NotNull(message = "上级模块不能为空")
    private Long parentId;
    /**
     * 类型 type
     */
    private String type;
    /**
     * 名称 name
     */
    @NotEmpty(message = "名称不能为空")
    private String name;
    /**
     * 编码（唯一的，java类名） code
     */
    @NotEmpty(message = "模块编码不能为空")
    private String code;
    /**
     * 操作 operate
     */
    @Size(min = 1, message = "至少选择一项")
    private int[] operate;
    /**
     * 图标 icon
     */
    private String icon;
    /**
     * 链接地址 url
     */
    private String url;
    /**
     * 排序 idx
     */
    private int idx;
}
