package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

@Data
public class RoleDeleteCmd {
    /**
     * ID id
     */
    private Long roleId;
}
