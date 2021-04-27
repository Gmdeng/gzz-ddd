package com.gzz.retail.application.system.command;

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

    public void execute(){

    }
}
