package com.gzz.core.validation.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * 密码验证器
 */
public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, String> {
    //public static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
    // 由大写至少一个+小写+数字组成
    public static final String PWD_PATTERN = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]))[\\w\\W]{6,}$";

    @Override
    public void initialize(CheckPassword constraintAnnotation) {
        //初始化，得到注解数据
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        if (value.length() < 6) {
            return false;
        }
        return value.matches(PWD_PATTERN);
    }
}
