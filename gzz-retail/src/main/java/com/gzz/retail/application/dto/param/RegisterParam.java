package com.gzz.retail.application.dto.param;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 注册入参
 */
@Data
@Setter
@Getter
public class RegisterParam {
    @Length(max = 20, message = "姓名长度不能大于20")
    @NotEmpty(message = "用户名不能为空")
    private String userName;
    @NotEmpty(message = "手机号不能空")
    @Pattern(regexp="1[1|2|3|4|5|6|7|8|9]\\d{9}", message="无效的手机号")
    private String mobileNo;
    @Length(max = 6, message = "密码必须为6位")
    private String passwd;
    @Email(message ="无效的邮箱地址")
    private String email;
}
