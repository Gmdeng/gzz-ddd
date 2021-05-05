package com.gzz.retail.application.system;

import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.assembler.ModelAssembler;
import com.gzz.retail.application.assembler.dto.TreeSelectDto;
import com.gzz.retail.application.system.dto.ModuleDto;
import com.gzz.retail.application.system.queries.ModuleQuery;
import com.gzz.retail.application.system.queries.UserQuery;
import com.gzz.retail.infra.defines.types.OperateType;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
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
    public List<ModuleDto> getModuleByPage(ModuleQuery query){
         List<ZModulePo> dataList = moduleMapper.findListByPage(query.toParam(), query.getPager());
         List<ModuleDto> list = BeanConvertUtil.convertList(ZModulePo.class, ModuleDto.class, dataList, (src,dest) ->{
            Set<OperateType> opers = Arrays.stream(OperateType.values()).filter(it->{
                return ((src.getOperate() & it.value()) == it.value());
            }).collect(Collectors.toSet());
            dest.setOperate(opers);
         });
         return list;
    }

    /**
     *
     * @return
     */
    public TreeSelectDto getTreeSelect(){
        List<ZModulePo> poList = moduleMapper.findLists(new ParamMap());
        TreeSelectDto dto = new TreeSelectDto("根目录", 0L);
        ModelAssembler.toTreeSelectNode(poList, dto);
//        return ModelAssembler.toTreeSelect(poList, dto);
        return dto;
    }
}
