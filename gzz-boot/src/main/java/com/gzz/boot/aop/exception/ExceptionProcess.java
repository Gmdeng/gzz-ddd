package com.gzz.boot.aop.exception;

import com.gzz.core.exception.BizzException;
import com.gzz.core.response.HttpResult;
import com.gzz.core.util.AopUtil;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 异常处理
 */
public abstract class ExceptionProcess implements IExceptionProcess {
    protected String getMessage(Throwable e) {
        if (e instanceof BizzException) {
            return ((BizzException) e).getDesc();
        } else if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> errors =
                    ((ConstraintViolationException) e).getConstraintViolations();
            return "参数错误 : " + AopUtil.buildErrorDisplayMsg(errors);
        } else {
            return "服务器错误";
        }

    }

    protected Integer getCode(Throwable e) {
        if (e instanceof BizzException) {
            return ((BizzException) e).getCode();
        }
        return 1;
    }


    protected String getDesc(Throwable e) {
        if (e instanceof BizzException) {
            return ((BizzException) e).getDesc();
        }
        return null;
    }

    @Override
    public Object processException(Throwable e) {
        return HttpResult.result(getCode(e), getMessage(e));
    }
}
