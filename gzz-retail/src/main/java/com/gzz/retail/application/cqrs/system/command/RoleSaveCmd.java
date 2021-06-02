package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private String notes;

    /**
     * 权限
     */
    //@NotEmpty(message = "权限不能为空permissions")
    private String perms[];

    private Pertion  pertion;

    private List<Pertion>  hasPerms;
}
