package com.gzz.retail.domain.system.repo;

import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.domain.system.entity.ModuleDo;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.mapper.IZRoleMapper;
import com.gzz.retail.infra.persistence.mapper.IZUserMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统磁
 */
@Component
public class SystemRepo {
    @Autowired
    private IZModuleMapper moduleMapper;
    @Autowired
    private IZRoleMapper roleMapper;
    @Autowired
    private IZUserMapper userMapper;

    /**
     * 模块保存
     *
     * @param m
     */
    public void saveModule(ModuleDo m) {
        //ZModule zModule = BeanUtil.deepCopy(m, ZModule.class);

        ZModulePo zModule = new ZModulePo();
        BeanUtils.copyProperties(m, zModule);
        zModule.setParentId(0L);
        // zModule.setUpdateOn(new Date());
        zModule.setUpdateBy("Testing");
        // zModule.setCreateOn(new Date());
        zModule.setCreateBy("Testing");
        if (zModule.getId() == null) {
            moduleMapper.insert(zModule);
        } else {
            moduleMapper.update(zModule);
        }
    }

    /**
     * @param moduleId
     * @return
     */
    public ModuleDo getModuleById(Long moduleId) {
        ZModulePo zModule = moduleMapper.getById(moduleId);
        ModuleDo m = new ModuleDo();
        BeanUtils.copyProperties(zModule, m);
        zModule.setUrl("wss://g-zz.com");
        moduleMapper.update(zModule);
        return m;
    }

    /**
     * @return
     */
    public List<ModuleDo> getModuleList(ParamMap param) {
        return moduleMapper.findList(param);
    }
}
