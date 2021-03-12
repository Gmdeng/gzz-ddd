package com.gzz.core.validation.custom;

import com.gzz.boot.registry.DomainRegistry;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *  自定定义验证
 */

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CustomValid.Validator.class)
@Documented
public @interface CustomValid {
    Class<? extends CustomValidator>[] value();
    String message() default "字段不符合条件约束";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * 内部类
     */
    @Slf4j
    static class Validator implements ConstraintValidator<CustomValid, Object> {
        CustomValid annotation;

        List<Class<? extends CustomValidator>> checkerClasses;

        Map<Class<? extends CustomValidator>, ? extends CustomValidator> allCheckers;

        @Override
        public void initialize(CustomValid constraintAnnotation) {
            annotation = constraintAnnotation;
            checkerClasses = Arrays.asList(annotation.value());
            allCheckers = DomainRegistry.beanMap(CustomValidator.class)
                    .values().stream()
                    .collect(Collectors.toMap(CustomValidator::getClass, Function.identity()));
        }

        @Override
        public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
            try {
                StringBuilder sb = new StringBuilder();
                checkerClasses.forEach(c->{
                    CustomValidator checker = allCheckers.get(c);
                    if(checker!=null){
                        try {
                            checker.check(object);
                        }catch (Exception e){
                            sb.append(e.getMessage()).append(" ");
                        }
                    }
                });
                if(sb.length() > 0) {
                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext
                            .buildConstraintViolationWithTemplate(sb.toString())
                            .addConstraintViolation();
                    return false;
                }else{
                    return true;
                }
            }catch (Exception e){
                log.warn("", e);
                return false;
            }
        }
    }
}
