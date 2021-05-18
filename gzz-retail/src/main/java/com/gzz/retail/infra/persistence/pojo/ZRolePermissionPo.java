package com.gzz.retail.infra.persistence.pojo;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * 角色权限 实体类
 * @author Auto-Builder
 * @CrateOn 2021-5-18 11:08:49
 */
@Data
public class ZRolePermissionPo  {
	/**
	 *  ID id
	 */
	private Long id;
	/**
	 *  角色ID roleId
	 */
	private Long roleId;
	/**
	 *  模块ID moduleId
	 */
	private Long moduleId;
	/**
	 *  拥有权限 hasPower
	 */
	private int hasPower;
}