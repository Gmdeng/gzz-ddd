package com.gzz.retail.domain.system.entity;

import com.gzz.retail.domain.system.primitive.RoleId;
import com.gzz.retail.infra.defines.CommStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 系统角色
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
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
