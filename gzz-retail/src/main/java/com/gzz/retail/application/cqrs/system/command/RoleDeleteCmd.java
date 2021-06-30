package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RoleDeleteCmd {
    /**
     * ID id
     */
    @NotEmpty(message = "角色ID不能为空")
    private Long roleId;
}
