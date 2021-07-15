package com.gzz.retail.infra.persistence.pojo;
import lombok.Data;
/**
 *  实体类
 * @author Auto-Builder
 * @CrateOn 2021-7-15 16:05:57
 */
@Data
public class PBrandPo  {
	/**
	 *  ID id
	 */
	private Long id;
	/**
	 *  中文名称 cnName
	 */
	private String cnName;
	/**
	 *  英文名称 enName
	 */
	private String enName;
	/**
	 *  LOGO图标 logo
	 */
	private String logo;
	/**
	 *  网站 website
	 */
	private String website;
	/**
	 *  品牌故事 stroy
	 */
	private String stroy;
	/**
	 *  简介 summary
	 */
	private String summary;
}