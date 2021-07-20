package com.gzz.retail.infra.persistence.pojo;

import lombok.Data;

import java.io.Serializable;
/**
 * 规格参数 实体类
 * @author Auto-Builder
 * @CrateOn 2021-7-20 16:37:04
 */
@Data
public class PSpecificationPo implements Serializable  {
	/**
	 *  ID id
	 */
	private Long id;
	/**
	 *  参数名 paramLabel
	 */
	private String paramLabel;
	/**
	 *  参数值 paramValue
	 */
	private String paramValue;
	/**
	 *  分组ID groupId
	 */
	private Long groupId;
}