package com.gzz.boot.aop.exception;

/**
 * 异常处理
 */
public interface IExceptionProcess {
    Class<?> processClass();

    Object processException(Throwable e);
}
