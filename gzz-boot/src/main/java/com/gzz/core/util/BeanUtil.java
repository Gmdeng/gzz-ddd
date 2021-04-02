package com.gzz.core.util;

import com.alibaba.fastjson.JSON;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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
    public static <T, S> void mergeB(T destObj, S srcObj) {
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
     * JAVA合并对象属性，把对象的非空属性合成到目标对象上
     * BeanMerge，对象属性合并(比beanCopy好用)
     * @Author zhengkai.blog.csdn.net
     */
    public static <M> void merge(M target, M destination) throws Exception {
        //获取目标bean
        BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
        // 遍历所有属性
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
            // 如果是可写属性
            if (descriptor.getWriteMethod() != null) {
                Object defaultValue = descriptor.getReadMethod().invoke(destination);
                //可以使用StringUtil.isNotEmpty(defaultValue)来判断
                if(defaultValue!=null && !"".equals(defaultValue)){
                    //用非空的defaultValue值覆盖到target去
                    descriptor.getWriteMethod().invoke(target, defaultValue);
                }
            }
        }
    }

    /**
     *
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
     *
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
