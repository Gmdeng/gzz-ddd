package com.gzz.boot.registry;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 注册到SPRING上下文中。
 */
public class DomainRegistry implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (DomainRegistry.applicationContext == null) {
            DomainRegistry.applicationContext = applicationContext;
        }
    }
    public static <T> T bean(Class<T> clazz) {
        if (applicationContext == null) {
            throw new RuntimeException("bean.not.exist");
        }
        Map beans = applicationContext.getBeansOfType(clazz);
        T t = (T) beans.values().stream().findFirst().orElse(null);

        if (t == null) {
            throw new RuntimeException("bean.not.exist");
        }
        return t;
    }

    public static <T> List<T> getBeanListOfType(Class<T> clazz) {
        List result = new ArrayList<>();
        Map<String, T> map = applicationContext.getBeansOfType(clazz);
        if (null != map) {
            result.addAll(map.values());
        }
        return result;
    }
    public static <T> Map<String, T> getBeanMapOfType(Class<T> clazz) {
        Map<String, T> map = applicationContext.getBeansOfType(clazz);
        return map;
    }

    public static Object getObject(String id) {
        Object object = null;
        object = applicationContext.getBean(id);
        return object;
    }

    // 获取BEAN
    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            throw new RuntimeException("applicationContext是空的");
        }
        T t = (T) applicationContext.getBean(clazz);
        if (t == null) {
            throw new RuntimeException("bean.not.exist");
        }
        return t;
    }

    public static <T> T service(Class<T> clazz) {
        return bean(clazz);
    }


    public static <T> T repo(Class<T> clazz) {
        return bean(clazz);
    }


    public static <T> List<T> allBeans(Class<T> clazz) {
        if (applicationContext == null) {
            throw new RuntimeException("bean.not.exist");
        }
        Map<String, T> beans = applicationContext.getBeansOfType(clazz);
        List<T> t = beans.values().stream().collect(Collectors.toList());

        if (t == null) {
            throw new RuntimeException("bean.not.exist");
        }
        return t;
    }

    public static <T> Map<String, T> beanMap(Class<T> clazz) {
        if (applicationContext == null) {
            throw new RuntimeException("bean.not.exist");
        }
        Map<String, T> beans = applicationContext.getBeansOfType(clazz);

        if (beans == null) {
            throw new RuntimeException("bean.not.exist");
        }
        return beans;
    }


}
