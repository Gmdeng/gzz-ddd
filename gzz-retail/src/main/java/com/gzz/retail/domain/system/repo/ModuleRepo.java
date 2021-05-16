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
    private IZModuleMapper mapper;

    /**
     * 审核
     * @param entity
     */
    public void audit(Module entity){
        ZModulePo m = mapper.getById(entity.getModuleId().getId());
        m.setStatus(entity.getStatus().getKey());
        mapper.update(m);
    }

    /**
     * 保存数据
     */
    public void save(Module entity){
        ZModulePo po = BeanConvertUtil.convertOne(entity, ZModulePo.class,  (src, dest)->{
            if(src.getModuleId()!=null)
                dest.setId(src.getModuleId().getId());
            dest.setParentId(src.getParent().getModuleId().getId());
            dest.setOperate(0);
            src.getOperates().forEach(operateType -> {
                dest.setOperate(Integer.sum(dest.getOperate(), operateType.getKey()));
            });
        });
        int num =0;

        if(po.getId()== null) {
            num = mapper.insert(po);
        }else{
            num = mapper.update(po);
        }
        if(num ==0)
            throw new BizzException("保存数据异常");
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delete(Module entity){
        mapper.delete(entity.getModuleId().getId());
    }
}
