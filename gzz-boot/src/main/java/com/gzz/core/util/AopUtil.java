package com.gzz.core.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import javax.validation.ConstraintViolation;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Aop工具类
 */
@Slf4j
public class AopUtil {

    /**
     * 获取方法
     *
     * @param pjp
     * @param annotationClass
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getAnnotationPresent(ProceedingJoinPoint pjp, Class<T> annotationClass) {
        Method method = getMethod(pjp);
        T annotation;
        annotation = method != null ? method.getAnnotation(annotationClass) : null;
        if (annotation != null) {
            return annotation;
        } else {
            Class<?> clazz = pjp.getTarget().getClass();
            annotation = clazz != null ? clazz.getAnnotation(annotationClass) : null;
            return annotation;
        }
    }

    public static <T extends Annotation> T getAnnotationPresent(JoinPoint pjp, Class<T> annotationClass) {
        Method method = getMethod(pjp);
        T annotation;
        annotation = method != null ? method.getAnnotation(annotationClass) : null;
        if (annotation != null) {
            return annotation;
        } else {
            Class<?> clazz = pjp.getTarget().getClass();
            annotation = clazz != null ? clazz.getAnnotation(annotationClass) : null;
            return annotation;
        }
    }

    /**
     * 获取方法
     *
     * @param pjp
     * @return
     */
    public static Method getMethod(ProceedingJoinPoint pjp) {
        Signature sig = pjp.getSignature();
        if (!(sig instanceof MethodSignature)) {
            log.error("getMethod encounter error, unsupport signature:" + sig.getName());
            return null;
        }
        MethodSignature msig = (MethodSignature) sig;
        Object target = pjp.getTarget();

        Method currentMethod = null;
        try {
            currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        } catch (NoSuchMethodException e) {
            log.error("getMethod encounter error, NoSuchMethodException " + sig.getName() + e);
        }
        return currentMethod;
    }
    public static Method getMethod(JoinPoint pjp) {
        Signature sig = pjp.getSignature();
        if (!(sig instanceof MethodSignature)) {
            log.error("getMethod encounter error, unsupport signature:" + sig.getName());
            return null;
        }
        MethodSignature msig = (MethodSignature) sig;
        Object target = pjp.getTarget();

        Method currentMethod = null;
        try {
            currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        } catch (NoSuchMethodException e) {
            log.error("getMethod encounter error, NoSuchMethodException " + sig.getName() + e);
        }
        return currentMethod;
    }
    /**
     * 错识信息
     *
     * @param constraintSet
     * @return
     */
    public static String buildErrorMsg(Set<ConstraintViolation<?>> constraintSet) {

        if (constraintSet != null && !constraintSet.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            constraintSet.forEach(
                    c -> sb.append(c.getPropertyPath()).append(":").append(c.getMessage()).append(","));
            return sb.deleteCharAt(sb.length() - 1).toString();
        } else {
            return "";
        }
    }


    /**
     * 显示错识信息
     *
     * @param constraintSet
     * @return
     */
    public static String buildErrorDisplayMsg(Set<ConstraintViolation<?>> constraintSet) {
        if (constraintSet != null && !constraintSet.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            constraintSet.forEach(c -> sb.append(c.getMessage()).append(","));
            return sb.deleteCharAt(sb.length() - 1).toString();
        } else {
            return "";
        }
    }
}
