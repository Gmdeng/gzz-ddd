package com.gzz.retail.application.cqrs.system;

import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.assembler.ModuleAssembler;
import com.gzz.retail.application.assembler.dto.TreeSelectDto;
import com.gzz.retail.application.cqrs.system.dto.MenuDto;
import com.gzz.retail.application.cqrs.system.dto.ModuleDto;
import com.gzz.retail.application.cqrs.system.dto.ModuleFormDto;
import com.gzz.retail.application.cqrs.system.queries.ModuleQuery;
import com.gzz.retail.infra.defines.CommStatus;
import com.gzz.retail.infra.defines.types.OperateType;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 模块Query
 */
@Slf4j
@Service
public class ModuleQueryApplication {
    @Autowired
    private IZModuleMapper moduleMapper;

    /**
     * 获取表单数据
     * @param moduleId
     * @return
     */
    public ModuleFormDto getModuleFormById(Long moduleId){
        ZModulePo zModule = moduleMapper.getById(moduleId);
        ModuleFormDto moduleDto = BeanConvertUtil.convertOne( zModule, ModuleFormDto.class, (src, dest)->{
            Set<Integer> opers = Arrays.stream(OperateType.values()).filter(it->{
                return ((src.getOperate() & it.getKey()) == it.getKey());
            }).map(m->{
                return m.getKey();
            }).collect(Collectors.toSet());
            dest.setOperate(opers);
        });
        return moduleDto;
    }

    /**
     * 获取详细
     * @param moduleId
     * @return
     */
    public ModuleDto getModuleDetailById(Long moduleId){
        ZModulePo zModule = moduleMapper.getById(moduleId);
        ModuleDto moduleDto = BeanConvertUtil.convertOne(zModule, ModuleDto.class,  (src, dest)->{
            Set<OperateType> opers = Arrays.stream(OperateType.values()).filter(it->{
                return ((src.getOperate() & it.getKey()) == it.getKey());
            }).collect(Collectors.toSet());
            dest.setOperate(opers);
            dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
        });
        return moduleDto;
    }

    /**
     * 获取分页面
     * @param query
     * @return
     */
    public List<ModuleDto> getModuleByPage(ModuleQuery query){
         List<ZModulePo> dataList = moduleMapper.findListByPage(query.toParam(), query.getPager());
         List<ModuleDto> list = BeanConvertUtil.convertList( dataList, ModuleDto.class, (src,dest) ->{
            Set<OperateType> opers = Arrays.stream(OperateType.values()).filter(it->{
                return ((src.getOperate() & it.getKey()) == it.getKey());
            }).collect(Collectors.toSet());
            dest.setOperate(opers);
            dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
         });
         return list;
    }

    /**
     * 获取所有
     * @param query
     * @return
     */
    public List<ModuleDto> getModuleTreeList(ModuleQuery query){
        List<ZModulePo> dataList = moduleMapper.findList(query.toParam());
        List<ModuleDto> list = BeanConvertUtil.convertList( dataList, ModuleDto.class, (src,dest) ->{
            Set<OperateType> opers = Arrays.stream(OperateType.values()).filter(it->{
                return ((src.getOperate() & it.getKey()) == it.getKey());
            }).collect(Collectors.toSet());
            dest.setOperate(opers);
            dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
            dest.setChildren(new ArrayList<>());
        });

        // 转目录树
        ModuleDto parent  = new ModuleDto();
        parent.setId(0L);
        parent.setChildren(new ArrayList<>());
        ModuleAssembler.toModuleNode(list, parent);
        return parent.getChildren();
    }

    /**
     *
     * @param query
     * @return
     */
    public List<ModuleDto> getModuleList(ModuleQuery query) {
        List<ZModulePo> dataList = moduleMapper.findList(query.toParam());
        List<ModuleDto> list = BeanConvertUtil.convertList( dataList, ModuleDto.class, (src,dest) ->{
            Set<OperateType> opers = Arrays.stream(OperateType.values()).filter(it->{
                return ((src.getOperate() & it.getKey()) == it.getKey());
            }).collect(Collectors.toSet());
            dest.setOperate(opers);
            dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
            dest.setChildren(new ArrayList<>());
        });

        return list;
    }

    /**
     * 树形目录选项
     *
     * @return
     */
    public TreeSelectDto getTreeSelect(){
        List<ZModulePo> poList = moduleMapper.findLists(new ParamMap());
        TreeSelectDto dto = new TreeSelectDto("根目录", 0L);
        ModuleAssembler.toTreeSelectNode(poList, dto);
//        return ModelAssembler.toTreeSelect(poList, dto);
        return dto;
    }

    public List<MenuDto> getMenuList(ModuleQuery query) {
        List<ZModulePo> poList = moduleMapper.findLists(new ParamMap());
        MenuDto root = new MenuDto(0L, "", "", "");
        return ModuleAssembler.toTreeMenus(poList, root);
    }
}
