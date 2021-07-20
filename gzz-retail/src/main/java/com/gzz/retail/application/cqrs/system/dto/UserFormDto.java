package com.gzz.retail.application.cqrs.system.dto;

import lombok.Data;

@Data
public class UserFormDto {
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
     * 邮箱 email
     */
    private String email;
    /**
     * 允许登录IP allowIpaddr
     */
    private String allowIpaddr;
    /**
     * 备注 notes
     */
    private String notes;

    /**
     * 角色IDS roles
     */
    private long[] roles;

}
