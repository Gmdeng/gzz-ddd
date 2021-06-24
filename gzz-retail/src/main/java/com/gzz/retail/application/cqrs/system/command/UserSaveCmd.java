package com.gzz.retail.application.cqrs.system.command;

import com.gzz.core.validation.validator.CheckMobileNo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Data
public class UserSaveCmd {
    /**
     * 模块ID
     */
    private Long id;
    /**
     * 用户名
     */
    @Length(max = 20, message = "姓名长度不能大于20")
    @NotEmpty(message = "用户名不能为空")
    private String userId;
    /**
     * 昵称
     */
    private String petName;
    /**
     * 手机号
     */
    //@Pattern(regexp = "1[1|2|3|4|5|6|7|8|9]\\d{9}", message = "无效的手机号")
    @CheckMobileNo()
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 允许登录IP
     */
    private String allowIpaddr;
    /**
     * 拒绝登录IP
     */
    private String denyIpaddr;
    /**
     * 描述
     */
    private String notes;
    /**
     * 角色
     */
    private String[] roles;
}
