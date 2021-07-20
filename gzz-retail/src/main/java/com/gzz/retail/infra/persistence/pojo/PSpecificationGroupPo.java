package com.gzz.retail.infra.persistence.pojo;

import lombok.Data;

import java.io.Serializable;
/**
 * 规格参数组 实体类
 * @author Auto-Builder
 * @CrateOn 2021-7-20 16:37:20
 */
@Data
public class PSpecificationGroupPo implements Serializable  {
	/**
	 *  ID id
	 */
	private Long id;
	/**
	 *  名称 name
	 */
	private String name;
}