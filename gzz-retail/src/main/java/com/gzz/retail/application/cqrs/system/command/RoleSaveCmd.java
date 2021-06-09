package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RoleSaveCmd {
    /**
     * ID id
     */
    private Long id;
    /**
     * 名称 name
     */
    @NotEmpty(message = "名称不能为空")
    private String name;
    /**
     * 编码（唯一的，java类名） code
     */
    @NotEmpty(message = "编码不能为空")
    private String code;
    /**
     * 排序 idx
     */
    private int idx;

    /**
     * 描述
     */
    @NotEmpty(message = "编码不能为空")
    private String notes;

    /**
     * 权限
     */
    @Valid // 嵌套验证必须用@Valid
    @NotNull(message = "权限不能为空")
    @Size(min = 1, message = "至少要有一个模块权限")
    private List<Privilege> privileges;
}
