package com.gzz.retail.infra;

import com.gzz.core.exception.BizzException;
import com.gzz.core.response.HttpResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;

import java.io.IOException;

/**
 * 全局异常处理
 *
 * @author G-m
 *
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {
    private final Logger log = LogManager.getLogger(GlobalExceptionAdvice.class);
    /**
     * 登录认证异常
     */
    @ExceptionHandler({ UnauthenticatedException.class, AuthenticationException.class })
    public HttpResult authenticationException(Exception ex) throws IOException {
        log.error("登录认证异常: ");
        return HttpResult.fail(ex.getMessage());
    }

    @ExceptionHandler(value = BizzException.class)
    public HttpResult exceptionHandler(NativeWebRequest request, BizzException ex) {
        log.error("数据处理异常BusinessException: "+ ex.getMessage());
        return HttpResult.fail(ex.getMessage());
    }

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public HttpResult exceptionHandler(NativeWebRequest request, Exception ex) {
        log.error("数据处理异常Exception: " + ex.getMessage() );
        if (ex instanceof NullPointerException) {
            log.error("数据处理异常Exception:空指针 " );
        }
        return HttpResult.fail(ex.getMessage()==null ?"系统异常" : ex.getMessage());
    }


}
