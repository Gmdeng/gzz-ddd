package com.gzz.retail.application.assembler;

import com.gzz.retail.application.assembler.dto.PermissionNodeDto;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;

import java.util.List;
import java.util.stream.Collectors;

public class PermissionAssembler {
    // 递归为目录树
    public static List<PermissionNodeDto> toTreeSelect(List<ZModulePo> source, PermissionNodeDto dto){
        return source.stream().filter(it -> dto.getModuleId().compareTo(it.getParentId()) == 0)
                .map(i->{
                    PermissionNodeDto dto3 = new PermissionNodeDto(i.getName(), i.getId());
                    dto3.setChildren(toTreeSelect(source, dto));
                    return dto3;
                }).collect(Collectors.toList());
    }

    /**
     * 递归为目录树
     * @param source
     * @param dto
     */
    public static void toTreeSelectNode(List<ZModulePo> source, PermissionNodeDto dto){
        for (ZModulePo po:source) {
            // 注意：从缓存获取的数据类型与
            if(dto.getModuleId().compareTo(po.getParentId()) == 0){
                PermissionNodeDto treeNode = new PermissionNodeDto(po.getName(),po.getId());
                dto.getChildren().add(treeNode);
                toTreeSelectNode(source, treeNode);
            }
        }

    }
}
