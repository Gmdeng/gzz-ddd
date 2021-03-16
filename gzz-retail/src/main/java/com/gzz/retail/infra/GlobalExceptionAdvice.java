package com.gzz.retail.infra;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzz.core.exception.BizzException;
import com.gzz.core.response.HttpResult;
import com.gzz.core.response.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author G-m
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {
    private final Logger log = LogManager.getLogger(GlobalExceptionAdvice.class);

    /**
     * 登录认证异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ExceptionHandler({UnauthenticatedException.class, AuthenticationException.class})
    public HttpResult authenticationException(Exception ex) throws IOException {
        log.error("登录认证异常: ");
        return HttpResult.fail(ResultCode.AUTH_ERROR).message(ex.getMessage());
    }

    /**
     * 自定义业务异常类型
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BizzException.class)
    public HttpResult bizzException(NativeWebRequest request, BizzException ex) {
        log.error("数据处理异常BusinessException: " + ex.getMessage());
        return HttpResult.fail(ex.getMessage());
    }

    /**
     * 方法参数校验异常 Validate
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public HttpResult handleValidationException(HttpServletRequest request, ConstraintViolationException ex) {
        log.error("数据处理异常ConstraintViolationException:" + request.getRequestURI(), ex);
        String collect = ex.getConstraintViolations().stream().filter(Objects::nonNull)
                .map(cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));
//        RestResultWrapper<String> restResultWrapper = new RestResultWrapper();
//        logger.info("请求参数异常",collect);
//        restResultWrapper.setCode(HttpStatus.BAD_REQUEST.value());
//        restResultWrapper.setMessage(ex.getMessage());
        return HttpResult.fail("访问请求参数不全");
    }

    /**
     * Bean 校验异常 Validate
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class) //400
    public HttpResult methodArgumentValidationHandler(HttpServletRequest request, MethodArgumentNotValidException exception) {
        log.error("数据处理异常MissingServletRequestParameterException: " + exception.getMessage());
        //https://blog.csdn.net/weixin_43549578/article/details/90242559
//        log.error("请求参数错误！{}",getExceptionDetail(exception),"参数数据："+showParams(request));
//        RestResultWrapper<String> restResultWrapper = new RestResultWrapper();
//        restResultWrapper.setCode(HttpStatus.BAD_REQUEST.value());
//        if (exception.getBindingResult() != null && !CollectionUtils.isEmpty(exception.getBindingResult().getAllErrors())) {
//            restResultWrapper.setMessage(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//        } else {
//            restResultWrapper.setMessage(exception.getMessage());
//        }
        return HttpResult.fail(ResultCode.PARAM_ERROR).message("访问请求参数不全");
    }

    /**
     * 请求参数不全异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public HttpResult missingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        log.error("数据处理异常MissingServletRequestParameterException: " + ex.getMessage());

        //"下的请求参数不全："+pe.getMessage());
        return HttpResult.fail(ResultCode.PARAM_ERROR).message("访问请求参数不全");
    }

    /**
     * 请求方式异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpResult httpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.error("数据处理异常HttpRequestMethodNotSupportedException: " + ex.getMessage());
        return HttpResult.fail(ResultCode.PARAM_ERROR).message("请求方式不正确");
    }

    /**
     * 绑定数据类型异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public HttpResult exceptionHandler(NativeWebRequest request, BindException ex) {
        log.error("数据处理异常BindException: " + ex.getMessage());

        Map<String, Object> map = new HashMap<>();
        if (ex.getBindingResult() != null) {
            List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
//            allErrors.stream().filter(Objects::nonNull).forEach(err -> {
//                System.out.println("-----stream().forEach--------");
//                System.out.println(err.getObjectName());
//                FieldError fieldError = (FieldError) err;
//                System.out.println("Field = " + fieldError.getField());
//
//                System.out.println("VALUE = " + fieldError.getRejectedValue());
//                System.out.println("TYPTE = " + fieldError.getCode());
//                System.out.println("TYPTE = " + fieldError.getCodes());
//                System.out.println("TYPTE = " + fieldError.getArguments());
//                System.out.println("ErrMsg = " + err.getDefaultMessage());
//                //log.error("--请求参数："+(((FieldError) ((FieldError) allErrors.get(0))).getField().toString() ), "===========>", objectError.getDefaultMessage());
//            });
           map= allErrors.stream().filter(Objects::nonNull).map(err->{
                return (FieldError) err;
            }).collect(Collectors.toMap(FieldError::getField, FieldError::getRejectedValue));
        }

        return HttpResult.fail(ResultCode.PARAM_ERROR).data(JSON.toJSON(map));
    }

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public HttpResult exceptionHandler(NativeWebRequest request, Exception ex) {
        log.error("数据处理异常Exception: " + ex.getMessage());
        if (ex instanceof NullPointerException) {
            log.error("数据处理异常Exception:空指针 ");
        }
        return HttpResult.fail(ex.getMessage() == null ? "系统异常" : ex.getMessage());
    }


}
