package com.gzz.retail.infra.persistence.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色 实体类
 *
 * @author Auto-Builder
 * @CrateOn 2020-10-10 10:56:15
 */
@Data
public class ZUserRolePo implements Serializable {
    /**
     * ID id
     */
    private Long id;
    /**
     * 模块ID userId
     */
    private Long userId;
    /**
     * 角色ID roleId
     */
    private Long roleId;
    /**
     * 角色名称 roleName
     */
    private String roleName;
}