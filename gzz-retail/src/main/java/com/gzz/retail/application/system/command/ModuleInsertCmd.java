package com.gzz.retail.application.system.command;

import com.gzz.retail.infra.defines.types.OperateType;

import java.util.Set;

public class ModuleInsertCmd {
    /**
     * ID id
     */
    private Long id;
    /**
     * 父模块ID parentId
     */
    private Long parentId;
    /**
     * 类型 type
     */
    private String type;
    /**
     * 名称 name
     */
    private String name;
    /**
     * 编码（唯一的，java类名） code
     */
    private String code;
    /**
     * 操作 operate
     */
    private Set<OperateType> operate;
    /**
     * 图标 icon
     */
    private String icon;
    /**
     * 链接地址 url
     */
    private String url;
    /**
     * 排序 idx
     */
    private int idx;
}
