package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

/**
 *
 */
@Data
public class RoleAuditCmd {
    /**
     * ID id
     */
    private Long roleId;

    /**
     * status
     */
    private int status;
}
