package com.gzz.core.util;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class BeanUtil {
    /**
     * 深度COPY对象
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deepCopy(Object obj, Class<T> clazz) {
        String json = JSON.toJSONString(obj);
        return JSON.parseObject(json, clazz);
    }

    /**
     * 深度COPY对象LIST
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> deepCopyList(List<?> obj, Class<T> clazz) {
        return JSON.parseArray(JSON.toJSONString(obj), clazz);
    }


    /**
     * 将源对象数据值复制替换目标对象
     *
     * @param destObj 目标对象
     * @param srcObj  源对象
     * @param <S>
     * @param <T>
     * @return
     */
    public static <T, S> void merge(T destObj, S srcObj) {
        Field[] fields = srcObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(srcObj);
                } catch (IllegalAccessException e) {
                    //e.printStackTrace();
                } finally {
                    field.setAccessible(false);
                }
                if (value == null) {
                    continue;
                }
                Field targetField = destObj.getClass().getDeclaredField(field.getName());
                if (targetField == null) continue;

                targetField.setAccessible(true);
                try {
                    targetField.set(destObj, value);
                } catch (IllegalAccessException e) {
                    //e.printStackTrace();
                } finally {
                    targetField.setAccessible(false);
                }
            } catch (NoSuchFieldException e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     * @param srcData
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T shadowCopy(S srcData, Class<T> targetClass) {
        return Optional.ofNullable(shadowCopyNullable(srcData, targetClass)).orElseThrow(() -> new IllegalArgumentException("bean.copy.fail"));
    }

    /**
     * @param srcData     源
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T shadowCopyNullable(S srcData, Class<T> targetClass) {
        T obj = null;
        try {
            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            obj = constructor.newInstance();
            constructor.setAccessible(false);
        } catch (Exception e) {
            return null;
        }
        merge(obj, srcData);
        return obj;
    }
}
