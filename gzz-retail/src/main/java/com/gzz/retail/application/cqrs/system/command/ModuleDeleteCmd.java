package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

@Data
public class ModuleDeleteCmd {
    /**
     * ID id
     */
    private Long moduleId;

}
