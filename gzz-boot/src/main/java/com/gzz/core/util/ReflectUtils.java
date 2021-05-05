package com.gzz.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 反射进行操作的一个工具类
 */
public class ReflectUtils {


    /**
     * 利用反射获取指定对象里面的指定属性
     *
     * @param obj       目标对象
     * @param fieldName 目标属性
     * @return 目标字段
     */
    private static Field getField(Object obj, String fieldName) {
        Field field = null;
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
            }
        }
        return field;
    }

    /**
     * 利用反射获取指定对象的指定属性
     *
     * @param clazz       目标对象
     * @param fieldName 目标属性
     * @return 目标属性的值
     */
    public static Object getFieldValue(Object clazz, String fieldName) {
        Object result = null;
        Field field = ReflectUtils.getField(clazz, fieldName);
        if (field != null) {
            boolean fAccess = field.isAccessible();

            try {
                field.setAccessible(true);
                result = field.get(clazz);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {
                field.setAccessible(fAccess);
            }
        }
        return result;
    }

    /**
     * 利用反射设置指定对象的指定属性为指定的值
     *
     * @param clazz      目标对象
     * @param fieldName  目标属性
     * @param fieldValue 目标属性值
     */
    public static void setFieldValue(Object clazz, String fieldName,
                                     Object fieldValue) {
        Field field = ReflectUtils.getField(clazz, fieldName);
//        if (!Modifier.isPublic(method.getModifiers())) {   //设置非共有方法权限
//            method.setAccessible(true);
//        }
        if (field != null) {
            boolean fAccess = false;
            try {
                fAccess = field.isAccessible();
                field.setAccessible(true);
                field.set(clazz, fieldValue);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {
                field.setAccessible(fAccess);
            }
        }
    }
}
