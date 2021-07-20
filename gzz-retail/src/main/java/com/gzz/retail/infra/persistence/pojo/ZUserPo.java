package com.gzz.retail.infra.persistence.pojo;

import com.gzz.boot.mybatis.autofill.AutoFillTime;
import com.gzz.boot.mybatis.autofill.AutoFillUser;
import com.gzz.retail.infra.defines.CommStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户 实体类
 *
 * @author Auto-Builder
 * @CrateOn 2020-10-10 10:55:57
 */
@Data
public class ZUserPo implements Serializable {
    /**
     * ID id
     */
    private Long id;
    /**
     * 用户名 userId
     */
    private String userId;
    /**
     * 密码 passwd
     */
    private String passwd;
    /**
     * 密码盐 salt
     */
    private String salt;
    /**
     * 名称 petName
     */
    private String petName;
    /**
     * 手机号 mobile
     */
    private String mobile;
    /**
     * 邮箱 eamil
     */
    private String email;
    /**
     * 允许登录IP allowIpaddr
     */
    private String allowIpaddr;

    /**
     * 拒绝登录IP denyIpaddr
     */
    private String denyIpaddr;
    /**
     * 备注 notes
     */
    private String notes;
    /**int
     * 状态 status
     */
    private CommStatus status;
    /**
     * 更新时间 updateOn
     */
    @AutoFillTime
    private Date updateOn;

    /**
     * 更新人 updateBy
     */
    @AutoFillUser
    private String updateBy;

    /**
     * 创建时间 createOn
     */
    @AutoFillTime
    private Date createOn;

    /**
     * 创建人 createBy
     */
    @AutoFillUser
    private String createBy;
}