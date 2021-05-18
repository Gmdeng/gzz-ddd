package com.gzz.retail.application.assembler;

import com.gzz.retail.application.assembler.dto.TreeSelectDto;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 对象 装配器
 */
public class TreeSelectAssembler {
    // 递归为目录树
    public static List<TreeSelectDto> toTreeSelect(List<ZModulePo> source, TreeSelectDto dto){
        return source.stream().filter(it -> dto.getValue().compareTo(it.getParentId()) == 0)
                .map(i->{
                    TreeSelectDto treeSelectDto = new TreeSelectDto(i.getName(), i.getId());
                    treeSelectDto.setChildren(toTreeSelect(source, treeSelectDto));
                    return treeSelectDto;
                }).collect(Collectors.toList());
    }

    /**
     * 递归为目录树
     * @param source
     * @param dto
     */
    public static void toTreeSelectNode(List<ZModulePo> source, TreeSelectDto dto){
        for (ZModulePo po:source) {
            // 注意：从缓存获取的数据类型与
            if(dto.getValue().compareTo(po.getParentId()) == 0){
                TreeSelectDto treeNode = new TreeSelectDto(po.getName(),po.getId());
                dto.getChildren().add(treeNode);
                toTreeSelectNode(source, treeNode);
            }
        }

    }
}
