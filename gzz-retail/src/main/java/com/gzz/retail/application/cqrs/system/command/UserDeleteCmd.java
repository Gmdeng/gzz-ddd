package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDeleteCmd{
    /**
     * ID id
     */
    @NotNull(message = "用户ID不能为空")
    private Long id;
    /**
     * 用户名 userId
     */
    private String userId;
}
