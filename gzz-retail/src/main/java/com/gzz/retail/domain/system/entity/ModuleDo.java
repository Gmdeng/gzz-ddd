package com.gzz.retail.domain.system.entity;

import com.gzz.retail.domain.system.primitive.ModuleId;
import com.gzz.retail.infra.defines.CommStatus;
import com.gzz.retail.infra.defines.types.OperateType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 系统模块
 */
@Data
@Accessors
public class ModuleDo {
    /**
     * ModuleId
     */
    private ModuleId moduleId;
    /**
     * 父模块ID parentId
     */
    private ModuleDo parent;
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
    private Set<OperateType> operates;
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
    /**
     * 状态 status
     */
    private CommStatus status;


    public ModuleDo(){}
    public ModuleDo(ModuleId id){
        this.moduleId = id;
    }

    public void save(){

    }

    public void delete(){

    }

    // 批准（approve）
    public void accept() {
        this.status = CommStatus.AUDIT;
    }
    // 拒绝
    public void reject() {
        this.status = CommStatus.DELETE;
    }
}
