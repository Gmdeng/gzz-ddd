package com.gzz.retail.application.system;

import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.assembler.ModelAssembler;
import com.gzz.retail.application.assembler.dto.TreeSelectDto;
import com.gzz.retail.application.system.dto.ModuleDto;
import com.gzz.retail.application.system.queries.UserQuery;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
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
        ZModulePo zModule = moduleMapper.getById(moduleId);
        ModuleDto moduleDto = BeanConvertUtil.convertOne(ZModulePo.class, ModuleDto.class, zModule);
        return moduleDto;
    }

    /**
     *
     * @param query
     * @return
     */
    public List<ModuleDto> getModuleByPage(UserQuery query){
        return moduleMapper.findListsByPage(query.toParam(), query.getPager());
    }

    /**
     *
     * @return
     */
    public List<TreeSelectDto> getTreeSelect(){
        List<ZModulePo> poList = moduleMapper.findLists(new ParamMap());
        return ModelAssembler.toTreeSelect(poList, new TreeSelectDto("根目录", 0L));
    }
}
