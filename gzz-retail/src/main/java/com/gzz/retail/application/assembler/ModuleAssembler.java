package com.gzz.retail.application.assembler;

import com.gzz.retail.application.assembler.dto.TreeSelectDto;
import com.gzz.retail.application.cqrs.system.dto.MenuDto;
import com.gzz.retail.application.cqrs.system.dto.ModuleDto;
import com.gzz.retail.application.assembler.dto.ActionOption;
import com.gzz.retail.application.assembler.dto.MenuNode;
import com.gzz.retail.infra.defines.types.OperateType;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 模块相关的 装配器
 */
public class ModuleAssembler {
    // 递归为目录树
    public static void toModuleNode(List<ModuleDto> source, ModuleDto parent){
        source.forEach(it->{
            if(parent.getId().compareTo(it.getParentId()) == 0 ){
                toModuleNode(source, it);  // 递归
                // if(parent.getChildren() == null) parent.setChildren(new ArrayList<>());
                parent.getChildren().add(it);
            }
        });
    }

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

    /**
     * 递归为目录树
     * @param source
     * @param node
     * @return
     */
    public static List<MenuNode> toTreeMenus(List<ZModulePo> source, MenuNode node){
        return source.stream()
                .filter(it-> node.getId().compareTo(it.getParentId())==0)
                .map(item ->{
                    MenuNode n = new MenuNode(item.getId(), item.getName());
                    List<ActionOption> optionList = Arrays.stream(OperateType.values()).filter(it->{
                        return ((item.getOperate() & it.getKey()) == it.getKey());
                    }).map(m->{
                        return new ActionOption(m.getKey(), m.getLabel());
                    }).collect(Collectors.toList());
//                    List<String> actions = new ArrayList<>();
//                    actions.add("Add");
//                    actions.add("Edit");
                    n.setNotes(optionList);
                    //
                    n.setChildren(toTreeMenus(source, n));
                    return n;
                }).collect(Collectors.toList());
    }


    public static List<MenuDto> toTreeMenus(List<ZModulePo> source, MenuDto node){
        return source.stream()
                .filter(it-> node.getId().compareTo(it.getParentId())==0)
                .map(item ->{
                    MenuDto n = new MenuDto(item.getId(), item.getCode(), item.getName(), item.getIcon());
                    //
                    n.setChildren(toTreeMenus(source, n));
                    return n;
                }).collect(Collectors.toList());
    }
}
