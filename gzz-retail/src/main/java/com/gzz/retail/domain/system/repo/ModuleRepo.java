package com.gzz.retail.domain.system.repo;

import com.gzz.core.exception.BizzException;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.domain.system.entity.Module;
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
    public void audit(Module module){
        ZModulePo m = moduleMapper.getById(module.getModuleId().getId());
        m.setStatus(module.getStatus().getKey());
        moduleMapper.update(m);
    }

    /**
     * 保存数据
     */
    public void save(Module module){
        ZModulePo zModule = BeanConvertUtil.convertOne(Module.class, ZModulePo.class, module, (src, dest)->{
            dest.setId(src.getModuleId().getId());
            dest.setParentId(src.getParent().getModuleId().getId());
        });
        int num =0;
        zModule.setOperate(7);
        if(zModule.getId()== null) {
            num = moduleMapper.insert(zModule);
        }else{
            num = moduleMapper.update(zModule);
        }
        if(num ==0)
            throw new BizzException("保存数据异常");
    }

    /**
     * 删除对象
     * @param module
     */
    public void delete(Module module){
        moduleMapper.delete(module.getModuleId().getId());
    }
}
