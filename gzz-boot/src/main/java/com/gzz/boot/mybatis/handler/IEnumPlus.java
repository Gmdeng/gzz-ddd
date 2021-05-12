package com.gzz.boot.mybatis.handler;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 通用通用枚举接口
 */
public interface IEnumPlus {
    String DEFAULT_VALUE_NAME = "key";
    String DEFAULT_LABEL_NAME = "label";

    default Integer getKey() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_VALUE_NAME);
        if (field == null)
            return null;
        try {
            field.setAccessible(true);
            return Integer.parseInt(field.get(this).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonValue
    default String getLabel() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_LABEL_NAME);
        if (field == null)
            return null;
        try {
            field.setAccessible(true);
            return field.get(this).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static <T extends Enum<T>> T keyOfEnum(Class<T> enumClass, Integer value) {
        if (value == null)
            throw  new IllegalArgumentException("DisplayedEnum value should not be null");
        if (enumClass.isAssignableFrom(IEnumPlus.class))
            throw new IllegalArgumentException("illegal DisplayedEnum type");
        T[] enums = enumClass.getEnumConstants();
        for (T t: enums) {
            IEnumPlus en = (IEnumPlus)t;
            if (en.getKey().equals(value))
                return (T) en;
        }
        throw new IllegalArgumentException("cannot parse integer: " + value + " to " + enumClass.getName());
    }
}
