package com.gzz.retail.domain.system;

import com.gzz.retail.domain.system.entity.ModuleDo;
import org.springframework.stereotype.Component;

/**
 * 模块工厂
 */
@Component
public class ModuleFactory {

    public ModuleDo buildModule(){
        return new ModuleDo();
    }

    public ModuleDo buildModule(Long moduleId){
        return new ModuleDo();
    }
}
