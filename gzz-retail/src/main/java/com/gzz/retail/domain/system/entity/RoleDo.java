package com.gzz.retail.domain.system.entity;

import com.gzz.retail.infra.defines.CommStatus;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 系统角色
 */
@Data
@Accessors(fluent = true)
public class RoleDo {
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
     * 状态 status
     */
    private CommStatus status;
}
