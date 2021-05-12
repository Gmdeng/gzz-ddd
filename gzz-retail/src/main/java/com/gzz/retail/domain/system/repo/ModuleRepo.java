package com.gzz.retail.domain.system.repo;

import com.gzz.core.exception.BizzException;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.domain.system.entity.Module;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 模块
 */
@Component
public class ModuleRepo {
    @Autowired
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
        ZModulePo zModule = BeanConvertUtil.convertOne(module, ZModulePo.class,  (src, dest)->{
            if(src.getModuleId()!=null)
                dest.setId(src.getModuleId().getId());
            dest.setParentId(src.getParent().getModuleId().getId());
            dest.setOperate(0);
            src.getOperates().forEach(operateType -> {
                dest.setOperate(Integer.sum(dest.getOperate(), operateType.getKey()));
            });
        });
        int num =0;

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
