package com.gzz.retail.application.assembler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 菜单节点
 */
@Data
@NoArgsConstructor
public class MenuNode {
    private Long id;
    private String name;
    private List<MenuNode> children;
    private List<ActionOption> notes;
    public MenuNode(Long id, String name){
        this.id =id;
        this.name = name;
    }
}
