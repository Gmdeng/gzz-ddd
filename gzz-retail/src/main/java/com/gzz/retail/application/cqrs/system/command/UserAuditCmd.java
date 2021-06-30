package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserAuditCmd {
    /**
     * ID id
     */
    @NotEmpty(message = "用户ID不能为空")
    private Long id;

    /**
     * status
     */
    private int status;
}
