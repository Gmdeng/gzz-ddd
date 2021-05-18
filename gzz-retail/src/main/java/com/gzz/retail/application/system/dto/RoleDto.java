package com.gzz.retail.application.system.dto;

import com.gzz.retail.domain.system.entity.Permission;
import com.gzz.retail.domain.system.primitive.RoleId;
import com.gzz.retail.infra.defines.CommStatus;
import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    /**
     * ID id
     */
    private RoleId roleId;
    /**
     * 名称 name
     */
    private String name;
    /**
     * 编码（唯一的，java类名） code
     */
    private String code;
    /**
     * 排序 idx
     */
    private int idx;

    /**
     * 排序 idx
     */
    private String notes;
    /**
     * 状态 status
     */
    private CommStatus status;

    /**
     * 许可
     */
    private List<Permission> permissions;
}
