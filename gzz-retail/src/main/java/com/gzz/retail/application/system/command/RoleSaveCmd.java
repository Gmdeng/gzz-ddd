package com.gzz.retail.application.system.command;

import com.gzz.retail.domain.system.primitive.RoleId;
import com.gzz.retail.infra.defines.CommStatus;
import lombok.Data;

import java.util.Set;

@Data
public class RoleSaveCmd {
    /**
     * ID id
     */
    private Long id;
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
     * 描述
     */
    private String desc;

    /**
     *
     */
    private Set<String> Permissions;
}
