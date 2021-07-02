package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserAuditCmd {
    /**
     * ID id
     */
    @NotNull(message = "用户ID不能为空")
    private Long id;

    /**
     * status
     */
    @NotNull(message = "状态不能为空")

    private Integer status;
}
