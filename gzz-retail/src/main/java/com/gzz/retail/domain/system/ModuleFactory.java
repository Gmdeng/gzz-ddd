package com.gzz.retail.domain.system;

import com.gzz.retail.domain.system.entity.Module;

/**
 * 模块工厂
 */
public class ModuleFactory {

    public Module buildModule(){
        return new Module();
    }

    public Module buildModule(Long moduleId){
        return new Module();
    }
}
