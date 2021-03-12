package com.gzz.retail.infra.persistence.pojo;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * 角色 实体类
 * @author Auto-Builder
 * @CrateOn 2020-10-10 10:55:08
 */
@Data
@Accessors
public class ZRole implements Serializable {
	/**
	 *  ID id
	 */
	private Long id;
	/**
	 *  名称 name
	 */
	private String name;
	/**
	 *  编码（唯一的，java类名） code
	 */
	private String code;
	/**
	 *  排序 idx
	 */
	private int idx;
	/**
	 *  状态 status
	 */
	private int status;
	/**
	 *  更新时间 updateOn
	 */
	private Date updateOn;
	/**
	 *  更新人 updateBy
	 */
	private String updateBy;
	/**
	 *  创建时间 createOn
	 */
	private Date createOn;
	/**
	 *  创建人 createBy
	 */
	private String createBy;
}