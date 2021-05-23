package com.gzz.retail.application.cqrs.system.dto;

import com.gzz.retail.application.assembler.dto.PermissionNodeDto;
import com.gzz.retail.domain.system.primitive.RoleId;
import lombok.Data;

import java.util.List;

@Data
public class RoleFormDto {
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
     * 许可
     */
    private List<PermissionNodeDto> permissions;
//    /**
//     * 许可
//     */
//    private List<Permission> permissions;
}
