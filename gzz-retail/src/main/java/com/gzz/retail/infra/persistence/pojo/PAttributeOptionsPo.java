package com.gzz.retail.infra.persistence.pojo;

import lombok.Data;

import java.io.Serializable;
/**
 * 分类销售属性值项 实体类
 * @author Auto-Builder
 * @CrateOn 2021-7-20 16:35:41
 */
@Data
public class PAttributeOptionsPo implements Serializable  {
	/**
	 *  ID id
	 */
	private Long id;
	/**
	 *  属性ID attrId
	 */
	private Long attrId;
	/**
	 *  选项名 name
	 */
	private String name;
}