package com.gzz.core.validation.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 关键词验证器
 */
public class CheckForbiddenValidator implements ConstraintValidator<CheckForbidden, String> {
    // 非法字符集
    private String[] forbiddenWords = {"admin"};

    @Override
    public void initialize(CheckForbidden constraintAnnotation) {
        //初始化，得到注解数据
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        for (String word : forbiddenWords) {
            if (value.contains(word)) {
                return false;//验证失败
            }
        }
        return true;
    }
}