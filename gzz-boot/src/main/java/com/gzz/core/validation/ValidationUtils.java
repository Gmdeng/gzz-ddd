package com.gzz.core.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 验证工具
 */
public class ValidationUtils {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 验证类
     *
     * @param obj 对象实例
     * @param <T>
     * @return
     */
    public static <T> ValidationResult validate(T obj) {

        return validate(obj, Default.class);
    }


    /**
     * 验证类 指定组
     *
     * @param obj    对象实例
     * @param groups 指定组
     * @param <T>
     * @return
     */
    public static <T> ValidationResult validate(T obj, Class<?>... groups) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validate(obj, groups);
        // if( CollectionUtils.isNotEmpty(set) ){
        if (set != null && set.size() != 0) {
            // result.setHasError(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
            result.setErrors(errorMsg);
        }

//        Set<ConstraintViolation<Object>> resultSet = validator.validate(obj, groups);
//        if (resultSet.size() > 0) {
//            //如果存在错误结果，则将其解析并进行拼凑后异常抛出
//            List<String> errorMessageList = resultSet.stream().map(o -> o.getMessage()).collect(Collectors.toList());
//            StringBuilder errorMessage = new StringBuilder();
//            errorMessageList.stream().forEach(o -> errorMessage.append(o + ";"));
//            throw new IllegalArgumentException(errorMessage.toString());
//        }
        return result;
    }

    /**
     * 验证类的属
     *
     * @param obj          对象实例
     * @param propertyName 属性名称
     * @param <T>
     * @return
     */
    public static <T> ValidationResult validate(T obj, String propertyName) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
        if (set != null && set.size() != 0) {
            result.setHasError(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(propertyName, cv.getMessage());
            }
            result.setErrors(errorMsg);
        }
        return result;
    }

}

