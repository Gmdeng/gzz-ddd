package com.gzz.retail.application.system;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.dto.ModuleDto;
import com.gzz.retail.application.system.query.UserQueryCmd;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class ModuleQueryApplication {
    @Autowired
    private IZModuleMapper moduleMapper;

    public ModuleDto getModuleById(Long moduleId){
        ZModule zModule = moduleMapper.getById(moduleId);
        ModuleDto moduleDto = BeanConvertUtil.convertOne(ZModule.class, ModuleDto.class, zModule);
        return moduleDto;
    }


    public List<ModuleDto> getModuleByPage(UserQueryCmd cmd){
        return moduleMapper.findListsByPage(cmd.toParam(), cmd.getPager());
    }
}
