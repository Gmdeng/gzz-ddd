package com.gzz.retail.application.assembler;

import com.gzz.retail.application.assembler.dto.TreeSelectDto;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 对象 装配器
 */
public class ModelAssembler {
    //
    public static List<TreeSelectDto> toTreeSelect(List<ZModulePo> source, TreeSelectDto dto){
        return source.stream().filter(it -> it.getParentId() == dto.getValue())
                .map(i->{
                    TreeSelectDto treeSelectDto = new TreeSelectDto(i.getName(), i.getId());
                    treeSelectDto.setChildren(toTreeSelect(source, treeSelectDto));
                    return treeSelectDto;
                }).collect(Collectors.toList());
    }
}
