package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

@Data
public class ModuleAuditCmd {
    /**
     * ID id
     */
    private Long moduleId;

    /**
     * status
     */
    private int status;

}
