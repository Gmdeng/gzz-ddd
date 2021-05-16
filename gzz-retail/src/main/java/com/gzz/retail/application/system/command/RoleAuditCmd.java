package com.gzz.retail.application.system.command;

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
