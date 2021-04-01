package com.gzz.core.validation.validator;

import org.springframework.util.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 密码验证
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
//指定验证器
@Constraint(validatedBy = CheckPassword.Validator.class)
@Documented
public @interface CheckPassword {

    //默认错误消息
    String message() default "必须是包含大写小写数字";

    //分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};

    //指定多个时使用
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CheckPassword[] value();
    }

    /**
     * 密码验证器
     */
    static class Validator implements ConstraintValidator<CheckPassword, String> {
        //public static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
        // 由大写至少一个+小写+数字组成
        public static final String PWD_PATTERN = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]))[\\w\\W]{6,}$";

        @Override
        public void initialize(CheckPassword constraintAnnotation) {
            //初始化，得到注解数据
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (StringUtils.isEmpty(value)) return true;

            if (value.length() < 6) return false;
            return value.matches(PWD_PATTERN);
        }
    }
}