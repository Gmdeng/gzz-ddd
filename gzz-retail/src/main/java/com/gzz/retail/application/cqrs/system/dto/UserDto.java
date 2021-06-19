package com.gzz.retail.application.cqrs.system.dto;

import com.gzz.boot.mybatis.autofill.AutoFillTime;
import com.gzz.boot.mybatis.autofill.AutoFillUser;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
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
     * 允许登录IP allowIpaddr
     */
    private String allowIpaddr;
    /**
     * 备注 notes
     */
    private String notes;
    /**int
     * 状态 status
     */
    private int status;
    /**
     * 更新时间 updateOn
     */
    private Date updateOn;

    /**
     * 更新人 updateBy
     */
    private String updateBy;

    /**
     * 创建时间 createOn
     */
    private Date createOn;

    /**
     * 创建人 createBy
     */
    private String createBy;
}