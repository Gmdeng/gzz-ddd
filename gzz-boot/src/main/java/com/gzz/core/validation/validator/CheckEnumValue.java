package com.gzz.core.validation.validator;

import lombok.SneakyThrows;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 */
///**
// * 定制化注解，支持参数值与指定类型数组列表值进行匹配(缺点是需要将枚举值写死在字段定义的注解中)
// */
//@EnumValueValid(intValues = {1, 2, 4, 8}, message = "订单类型错误")
//@EnumValueValid(strValues = {"pay", "refund"}, message = "订单类型错误")
//private String orderType;
///**
// * 定制化注解，实现参数值与枚举列表的自动匹配校验(能更好地与实际业务开发匹配)
// */
//@EnumValueValid(enumValues = Status.class, message = "状态值不在指定范围")
//private String status;
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {CheckEnumValue.Validator.class})
@Documented
public @interface CheckEnumValue {

    //默认错误消息
    String message() default "必须为指定值";

    //支持string数组验证
    String[] strValues() default {};

    //支持int数组验证
    int[] intValues() default {};

    //支持枚举列表验证
    Class<?>[] enumValues() default {};

    //分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};

    //指定多个时使用
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CheckEnumValue[] value();
    }

    /**
     * 校验类逻辑定义
     */
    static class Validator implements ConstraintValidator<CheckEnumValue, Object> {

        //字符串类型数组
        private String[] strValues;
        //int类型数组
        private int[] intValues;
        //枚举类
        private Class<?>[] enumValues;

        /**
         * 初始化方法
         *
         * @param constraintAnnotation
         */
        @Override
        public void initialize(CheckEnumValue constraintAnnotation) {
            strValues = constraintAnnotation.strValues();
            intValues = constraintAnnotation.intValues();
            enumValues = constraintAnnotation.enumValues();
        }

        /**
         * 校验方法
         *
         * @param value
         * @param context
         * @return
         */
        @SneakyThrows
        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            //针对字符串数组的校验匹配
            if (strValues != null && strValues.length > 0) {
                //判断值类型是否为String类型
                if (value instanceof String) {
                    for (String s : strValues)
                        if (s.equalsIgnoreCase(value.toString())) return true;
                }
            }
            //针对整型数组的校验匹配
            if (intValues != null && intValues.length > 0) {
                //判断值类型是否为Integer类型
                if (value instanceof Integer) {
                    for (Integer s : intValues)
                        if (s.compareTo((int)value) == 0) return true;
                }
            }
            //针对枚举类型的校验匹配
            if (enumValues != null && enumValues.length > 0) {
                for (Class<?> cl : enumValues) {
                    if (cl.isEnum()) {
                        //枚举类验证
                        Object[] objs = cl.getEnumConstants();
                        //这里需要注意，定义枚举时，枚举值名称统一用value表示
                        Method method = cl.getMethod("getValue");
                        for (Object obj : objs) {
                            Object code = method.invoke(obj);
                            if (value.equals(code.toString())) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }
}
