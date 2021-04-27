package com.gzz.retail.domain.system.repo;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.domain.system.entity.ModuleDo;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import org.springframework.stereotype.Component;

/**
 * 模
 */
@Component
public class ModuleRepo {
    private IZModuleMapper moduleMapper;

    /**
     * 审核
     * @param module
     */
    public void approve(ModuleDo module){
        ZModulePo m = moduleMapper.getById(module.getModuleId().getId());
        m.setStatus(m.getStatus());
        moduleMapper.update(m);
    }

    /**
     * 保存数据
     */
    public void save(ModuleDo module){
        ZModulePo zModule = BeanConvertUtil.convertOne(ModuleDo.class, ZModulePo.class, module, (src, dest)->{
            dest.setId(src.getModuleId().getId());
            dest.setParentId(src.getParent().getModuleId().getId());
        });
        if(zModule.getId()== null) {
            moduleMapper.insert(zModule);
        }else{
            moduleMapper.update(zModule);
        }
    }
}
