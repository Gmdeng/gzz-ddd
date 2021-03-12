package com.gzz.retail.infra.persistence.pojo;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 *  实体类
 * @author Auto-Builder
 * @CrateOn 2020-10-10 11:02:06
 */
@Data
@Accessors
public class PCatalog  {
	/**
	 *  id id
	 */
	private Long id;
	/**
	 *  parent_id parentId
	 */
	private String parentId;
	/**
	 *  name name
	 */
	private String name;
	/**
	 *  thumb thumb
	 */
	private String thumb;
	/**
	 *  idx idx
	 */
	private Byte idx;
	/**
	 *  keywords keywords
	 */
	private String keywords;
	/**
	 *  notes notes
	 */
	private String notes;
}