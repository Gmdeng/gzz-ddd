package com.gzz.retail.application.assembler;

import com.gzz.retail.application.assembler.dto.TreeSelectDto;
import com.gzz.retail.application.cqrs.goods.dto.CatalogDto;
import com.gzz.retail.infra.persistence.pojo.PCatalogPo;

import java.util.List;

public class CatalogAssembler {
    // 递归为目录树
    public static void toModuleNode(List<CatalogDto> source, CatalogDto parent){
        source.forEach(it->{
            if(parent.getId().compareTo(it.getParentId()) == 0 ){
                toModuleNode(source, it);  // 递归
                // if(parent.getChildren() == null) parent.setChildren(new ArrayList<>());
                parent.getChildren().add(it);
            }
        });
    }
    /**
     * 递归为目录树
     * @param source
     * @param dto
     */
    public static void toTreeSelectNode(List<PCatalogPo> source, TreeSelectDto dto){
        for (PCatalogPo po:source) {
            // 注意：从缓存获取的数据类型与
            if(dto.getValue().compareTo(po.getParentId()) == 0){
                TreeSelectDto treeNode = new TreeSelectDto(po.getName(),po.getId());
                dto.getChildren().add(treeNode);
                toTreeSelectNode(source, treeNode);
            }
        }
    }
}
