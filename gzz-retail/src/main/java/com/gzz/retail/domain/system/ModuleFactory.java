package com.gzz.retail.domain.system;

import com.gzz.retail.domain.system.entity.Module;
import org.springframework.stereotype.Component;

/**
 * 模块工厂
 */
@Component
public class ModuleFactory {

    public Module buildModule(){
        return new Module();
    }

    public Module buildModule(Long moduleId){
        return new Module();
    }
}
