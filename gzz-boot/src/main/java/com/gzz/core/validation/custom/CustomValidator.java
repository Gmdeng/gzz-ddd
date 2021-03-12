package com.gzz.core.validation.custom;

/**
 * 自定义验证接口
 * @param <T>
 */
public interface CustomValidator<T>{
    void check(T t);
}
