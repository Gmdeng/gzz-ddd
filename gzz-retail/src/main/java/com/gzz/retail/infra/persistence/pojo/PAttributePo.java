package com.gzz.retail.infra.persistence.pojo;

import lombok.Data;

import java.io.Serializable;
/**
 * 分类销售属性 实体类
 * @author Auto-Builder
 * @CrateOn 2021-7-20 16:35:28
 */
@Data
public class PAttributePo implements Serializable  {
	/**
	 *  ID id
	 */
	private Long id;
	/**
	 *  分类ID catalogId
	 */
	private Long catalogId;
	/**
	 *  属性名称 name
	 */
	private String name;
	/**
	 *  属性值项，用逗号隔里 values
	 */
	private String values;
	/**
	 *  排序 idx
	 */
	private Byte idx;
}