package com.gzz.core.validation.validator;

import lombok.SneakyThrows;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {CheckEnumPlus.Validator.class})
@Documented
public @interface CheckEnumPlus {
    //默认错误消息
    String message() default "与指定类型不相符";

    // 枚举类型
    En enumClass();

    // 枚举类型中的验证方法
    String enumMethod();

    //分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};
    /**
     * 校验类逻辑定义
     */
    static class Validator implements ConstraintValidator<CheckEnumPlus, String> {
        private Class<? extends Enum<?>> enumClass;
        //private String enumMethod;
        @Override
        public void initialize(CheckEnumPlus constraintAnnotation) {
            //enumMethod = constraintAnnotation.enumMethod();
            enumClass = constraintAnnotation.enumClass();
        }

        @SneakyThrows
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            Enum e = enumClass.newInstance();
            e.
//            Class<?> valueClass = value.getClass();
//            try {
//                Method method = enumClass.getMethod(enumMethod, valueClass);
//                if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
//                    throw new RuntimeException(String.format("%s method return is not boolean type in the %s class", enumMethod, enumClass));
//                }
//
//                if(!Modifier.isStatic(method.getModifiers())) {
//                    throw new RuntimeException(String.format("%s method is not static method in the %s class", enumMethod, enumClass));
//                }
//
//                Boolean result = (Boolean)method.invoke(null, value);
//                return result == null ? false : result;
//            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (NoSuchMethodException | SecurityException e) {
//                throw new RuntimeException(String.format("This %s(%s) method does not exist in the %s", enumMethod, valueClass, enumClass), e);
//            }
            //return false;
        }
    }
}
