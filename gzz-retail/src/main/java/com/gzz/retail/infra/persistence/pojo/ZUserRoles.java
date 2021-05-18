package com.gzz.retail.infra.persistence.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户角色 实体类
 *
 * @author Auto-Builder
 * @CrateOn 2020-10-10 10:56:15
 */
@Data
public class ZUserRoles {
    /**
     * ID id
     */
    private Long id;
    /**
     * 角色ID roleId
     */
    private Long roleId;
    /**
     * 模块ID userId
     */
    private Long userId;
    /**
     * 是否启用 enable
     */
    private Boolean enable;
}