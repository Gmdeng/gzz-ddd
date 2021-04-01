package com.gzz.core.validation.validator;

import com.gzz.core.validation.custom.CustomValid;
import lombok.extern.slf4j.Slf4j;
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
 * 非法关键词
 * 关键词验证器
 *
 * @
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
//指定验证器
@Constraint(validatedBy = CheckForbidden.Validator.class)
@Documented
public @interface CheckForbidden {

    //默认错误消息
    String message() default "有非法关键词";

    //分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};

    //指定多个时使用
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CheckForbidden[] value();
    }

    @Slf4j
    static class Validator implements ConstraintValidator<CheckForbidden, String> {
        // 非法字符集
        private String[] forbiddenWords = {"admin"};
        @Override
        public void initialize(CheckForbidden constraintAnnotation) {
            //初始化，得到注解数据
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (StringUtils.isEmpty(value)) return true;

            for (String word : forbiddenWords) {
                if (value.contains(word))
                    return false;//验证失败
            }
            return true;
        }
    }
}