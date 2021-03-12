package com.gzz.retail.infra.persistence.pojo;
import java.io.Serializable;
import java.util.Date;

import com.gzz.retail.infra.defines.CommStatus;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * 用户 实体类
 * @author Auto-Builder
 * @CrateOn 2020-10-10 10:55:57
 */
@Data
@Accessors
public class ZUser implements Serializable {
	/**
	 *  ID id
	 */
	private Long id;
	/**
	 *  用户名 userId
	 */
	private String userId;
	/**
	 *  密码 passwd
	 */
	private String passwd;
	/**
	 *  密码盐 salt
	 */
	private String salt;
	/**
	 *  名称 petName
	 */
	private String petName;
	/**
	 *  手机号 mobile
	 */
	private String mobile;
	/**
	 *  允许登录IP allowIpaddr
	 */
	private String allowIpaddr;
	/**
	 *  备注 notes
	 */
	private String notes;
	/**
	 *  状态 status
	 */
	private CommStatus status;
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