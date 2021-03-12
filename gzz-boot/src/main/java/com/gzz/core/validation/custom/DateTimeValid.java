package com.gzz.core.validation.custom;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 日期验证
 */
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DateTimeValid.Validator.class)
@Documented
public @interface DateTimeValid {
    /**
     * 错误消息  - 关键字段
     *
     * @return 默认错误消息
     */
    String message() default "日期格式错误";

    /**
     * 格式
     *
     * @return 验证的日期格式
     */
    String format() default "yyyy-MM-dd";

    /**
     * 允许我们为约束指定验证组 - 关键字段（TODO 下一章中会介绍）
     *
     * @return 分组
     */
    Class<?>[] groups() default {};

    /**
     * payload - 关键字段
     *
     * @return 暂时不清楚, 知道的欢迎留言交流
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 内部类
     */
    @Slf4j
    static class Validator implements ConstraintValidator<DateTimeValid, String> {
        DateTimeValid annotation;

        @Override
        public void initialize(DateTimeValid constraintAnnotation) {
            annotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            // 如果 value 为空则不进行格式验证，为空验证可以使用
            // @NotBlank @NotNull @NotEmpty 等注解来进行控制，职责分离
            if (value == null) {
                return true;
            }
            String format = annotation.format();
            if (value.length() != format.length()) {
                return false;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            try {
                simpleDateFormat.parse(value);
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
    }
}
