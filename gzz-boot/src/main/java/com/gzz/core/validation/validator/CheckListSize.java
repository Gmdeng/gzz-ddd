package com.gzz.core.validation.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
//指定验证器
@Constraint(validatedBy = CheckListSize.Validator.class)
@Documented
public @interface CheckListSize {
    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "List集合大小错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 定义List，为了让Bean的一个属性上可以添加多套规则
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface Lists {
        CheckListSize[] value();
    }
    @Slf4j
    static class Validator implements ConstraintValidator<CheckListSize, List> {

        private int min;
        private int max;

        @Override
        public void initialize(CheckListSize constraintAnnotation) {
            this.min = constraintAnnotation.min();
            this.max = constraintAnnotation.max();
        }

        @Override
        public boolean isValid(List list, ConstraintValidatorContext context) {
            if (list != null) {
                if (list.size() < min || list.size() > max) {
                    return false;
                }
            }
            return true;
        }
    }
}
