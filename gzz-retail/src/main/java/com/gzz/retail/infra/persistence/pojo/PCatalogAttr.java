package com.gzz.retail.infra.persistence.pojo;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 *  实体类
 * @author Auto-Builder
 * @CrateOn 2020-10-10 11:01:57
 */
@Data
@Accessors
public class PCatalogAttr  {
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
}