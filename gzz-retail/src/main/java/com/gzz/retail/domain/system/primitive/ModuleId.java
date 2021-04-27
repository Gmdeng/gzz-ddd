package com.gzz.retail.domain.system.primitive;

import lombok.Data;

/**
 *
 */
@Data
public class ModuleId {
    private long id;
    public ModuleId(Long moduleId){
        if(moduleId == null){
            throw new IllegalArgumentException("非法参数");
        }
        this.id = moduleId;
    }
}
