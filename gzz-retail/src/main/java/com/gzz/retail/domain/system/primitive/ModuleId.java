package com.gzz.retail.domain.system.primitive;

import lombok.Data;

import java.util.Objects;

/**
 *
 */
@Data
public class ModuleId {
    private long id;
    public ModuleId(Long moduleId){
        Objects.requireNonNull(moduleId, "非法参数");
//        if(moduleId == null)
//            throw new IllegalArgumentException("非法参数");
        this.id = moduleId;
    }
}
