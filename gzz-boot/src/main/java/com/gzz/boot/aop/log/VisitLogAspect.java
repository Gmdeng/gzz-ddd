package com.gzz.boot.aop.log;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.gzz.core.exception.BizzException;
import com.gzz.core.request.BaseRequest;
import com.gzz.core.request.Operator;
import com.gzz.core.request.Request;
import com.gzz.core.util.AopUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 访问日志切面 处理
 */
@Aspect
@Order(2)
public class VisitLogAspect {
    private static final Logger logger = LoggerFactory.getLogger("PARAM");
    private static final int SLOW_QUERY = 100;
    /**
     * method 和 class 都可以被切面到，如果一个类已经被 类处理过了 那么切面不在处理。
     */
    private static final ThreadLocal<Map<String, Local>> processMethod = ThreadLocal.withInitial(HashMap::new);
    private Set<Integer> globalWarnCode = new HashSet<>();
    private ConcurrentMap<String, CopyOnWriteArraySet<Integer>> methodWarnCode = new ConcurrentHashMap<>();
    private Map<String, Collection<Integer>> partErrorCode = new HashMap<>();

    @Pointcut("execution(public * com.yuwen.spring.demo.controller.*.*(..))")
    public void VisitLogAspect() {
    }

    /**
     * 方法切口
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.gzz.boot.aop.log.VisitLog)")
    public Object doRequestMethod(ProceedingJoinPoint pjp) throws Throwable {
        return execWithLogHandle(pjp);
    }

    /**
     * 类切口
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@within(com.gzz.boot.aop.log.VisitLog)")
    public Object doRequestClass(ProceedingJoinPoint pjp) throws Throwable {
        return execWithLogHandle(pjp);
    }

    /**
     * 执行切面的处理
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    private Object execWithLogHandle(ProceedingJoinPoint pjp) throws Throwable {
        String name = pjp.getSignature().getName();
        Map<String, Local> methodMap = processMethod.get();
        Local local = methodMap.get(name);
        boolean outermost = false;
        //如果 没有执行过切面那么执行
        if (local == null) outermost = true;

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());

        try {
            methodMap.put(name, new Local());
            //处理App 切入参数
            convertAndFillArgs(pjp);

            long startTime = System.currentTimeMillis();
            Object[] args = pjp.getArgs();
            String methodName = pjp.getSignature().toShortString();

            VisitLog annotation = AopUtil.getAnnotationPresent(pjp, VisitLog.class);
            if (annotation.open()) {
                try {
                    Object response = pjp.proceed();
                    logParam(annotation, startTime, methodName, args, response, null, outermost);
                    return response;
                } catch (Exception e) {
                    logParam(annotation, startTime, methodName, args, null, e, outermost);
                    throw e;
                } catch (Throwable e) {
                    logger.error("paramLog has Error ", e);
                    throw e;
                }
            } else {
                logger.info("has print log method:{}", name);
                return pjp.proceed();
            }
        } finally {
            //保底 remove 掉threadLocal
            if (outermost) {
                processMethod.remove();
            }
        }


    }

    /**
     * 转化填充参数
     */
    private void convertAndFillArgs(ProceedingJoinPoint pjp) {
        String userAgent = "";
        if (null != pjp.getArgs()) {
            for (Object arg : pjp.getArgs()) {
                if (arg != null && arg instanceof BaseRequest) {
                    BaseRequest request = (BaseRequest) arg;
                    if (Strings.isNullOrEmpty(((Request) arg).getSource())) {
                        if (Strings.isNullOrEmpty(userAgent) && Strings.isNullOrEmpty(((Request) arg).getSource())) {
                            return;
                        }
                        request.setSource(Strings.isNullOrEmpty(request.getSource()) ? userAgent : request.getSource());
                    }
                }
                break;
            }
        }
    }


    /**
     * 记录方法名称 入参,返回参数
     *
     * @param startTime  开始时间
     * @param methodName 方法名称
     * @param args       参数
     * @param response   返回值
     * @param e          异常可以为null
     */
    private void logParam(VisitLog annotation, long startTime, String methodName, Object[] args, Object response,
                          Exception e, boolean outermost) {

        long execTime = (System.currentTimeMillis() - startTime);

        //获取初始化参数
        Map<String, Object> param = initParam(methodName, args, e, execTime);

        //获取子参数
        Map<String, Object> subParams = processParam(methodName, args);
        if (subParams != null) {
            param.putAll(subParams);
        }

        //添加本地日志
        addLocalLog(annotation, methodName, args, response, param);

        //获取返回值
        String str = getResponseContent(annotation, param);

        //打印本地日志
        printLog(methodName, e, str, outermost, annotation.onlyOutermostPrint());

    }

    private void addLocalLog(VisitLog annotation, String methodName, Object[] args, Object response,
                             Map<String, Object> param) {
        param.put("method", methodName);
        param.put("param", args);
        if (annotation.logResponse()) {
            param.put("response", response);
        }
    }

    private Map<String, Object> initParam(String methodName, Object[] args, Exception e, long execTime) {
        Map<String, Object> param = new HashMap<>();
        param.put("execTime", execTime);
        if (isSlowRequest(execTime)) {
            param.put("slowQuery", true);
        } else {
            param.put("slowQuery", false);
        }
        String result;

        if (e == null) {
            result = "success";
        } else {
            if (isWarnException(methodName, e)) {
                result = "failed";
            } else {
                result = "error";
            }
            param.put("code", getCode(e));
            param.put("exception", getMessage(e));
        }
        param.put("result", result);
        if (args != null) {
            for (Object arg : args) {
                if (arg != null && arg instanceof Request) {
                    Request request = (Request) arg;
                    param.put("adminId", Optional.ofNullable(request.getOperator()).map(Operator::getUserId).orElse(null));
                    param.put("source", request.getSource());
                    break;
                }
            }

        }
        return param;
    }

    /**
     * 打印日志
     *
     * @param methodName
     * @param e
     * @param str
     * @param outermost
     * @param onlyOutermostPrint
     */
    private void printLog(String methodName, Exception e, String str, boolean outermost, boolean onlyOutermostPrint) {
        if (e == null) {
            if (!onlyOutermostPrint || outermost) {
                logger.info(str);
            }
        } else {
            String exceptionInfo = getExceptionInfo(e);
            if (e instanceof ConstraintViolationException) {
                if (!onlyOutermostPrint || outermost) {
                    logger.warn(str + ", exception=" + exceptionInfo);
                }

            } else if (isWarnException(methodName, e)) {
                if (!onlyOutermostPrint || outermost) {
                    logger.warn(str + ", exception=" + exceptionInfo);
                }
            } else {
                if (e instanceof BizzException) {
                    logBizEx((BizzException) e, exceptionInfo, str, !onlyOutermostPrint || outermost);
                } else {

                    logger.error(str + ", exception=" + exceptionInfo, e);
                }
            }
        }
    }

    /**
     * 打印业务异常信息
     *
     * @param ex
     * @param exceptionInfo
     * @param str
     * @param outermost
     */
    private void logBizEx(BizzException ex, String exceptionInfo, String str, boolean outermost) {
        System.out.println(ex.getMessage());
        System.out.println("");
//        ErrorLevel errorLevel = ex.getErrorLevel();
//        if (null != errorLevel && outermost) {
//            switch (errorLevel) {
//                case INFO:
//                    PARAM_API.info(str + ", exception=" + exceptionInfo, ex);
//                    break;
//                case WARN:
//                    PARAM_API.warn(str + ", exception=" + exceptionInfo, ex);
//                    break;
//                case DEBUG:
//                    PARAM_API.debug(str + ", exception=" + exceptionInfo, ex);
//                    break;
//                default:
//                    PARAM_API.error(str + ", exception=" + exceptionInfo, ex);
//                    break;
//            }
//        } else {
//            PARAM_API.error(str + ", exception=" + exceptionInfo, ex);
//        }
    }

    /**
     * 格式化响应内容
     *
     * @param annotation
     * @param param
     * @return
     */
    private String getResponseContent(VisitLog annotation, Map<String, Object> param) {
        String str = JSON.toJSONString(param);
        return str;
    }

    /**
     * 处理参数
     *
     * @param methodName
     * @param args
     * @return
     */
    protected Map<String, Object> processParam(String methodName, Object[] args) {
        return null;
    }

    /**
     * 格式化异常信息
     *
     * @param e
     * @return
     */
    private String getExceptionInfo(Exception e) {
        if (e != null) {
            Integer code = getCode(e);
            String message = getMessage(e);
            return " class: " + e.getClass() + " , code=" + code + " , message=" + message;
        } else {
            return "";
        }

    }

    /**
     * 获取异常信息
     *
     * @param e
     * @return
     */
    private String getMessage(Exception e) {
        String message = e.getMessage();
        if (e instanceof BizzException) {
            message = ((BizzException) e).getDesc();
        } else if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> errors = ((ConstraintViolationException) e).getConstraintViolations();
            message = "参数错误 : " + AopUtil.buildErrorMsg(errors);
        }
        return message;
    }

    /**
     * 获取异常编码
     *
     * @param e
     * @return
     */
    private Integer getCode(Exception e) {
        Integer code = 0;
        if (e instanceof BizzException) {
            code = ((BizzException) e).getCode();
        } else if (e instanceof ConstraintViolationException) {
            code = -101;
        }
        return code;
    }


    protected boolean isSlowRequest(long execTime) {
        return execTime > SLOW_QUERY;
    }


    protected boolean isWarnException(String methodName, Exception e) {
        if (e instanceof BizzException) {
            Integer code = ((BizzException) e).getCode();
            if (code != null) {
                return isWarnCode(methodName, code);
            }
        } else if (e instanceof ConstraintViolationException) {
            return true;
        }
        return false;
    }

    private boolean isWarnCode(String methodName, Integer code) {
        boolean isWarnCode = globalWarnCode.contains(code);
        if (!isWarnCode) {
            CopyOnWriteArraySet<Integer> errorCodes = methodWarnCode.get(methodName);
            if (errorCodes == null) {
                errorCodes = initMethodWarnCode(methodName);
                methodWarnCode.putIfAbsent(methodName, errorCodes);
            }
            isWarnCode = errorCodes.contains(code);
        }

        return isWarnCode;
    }

    private CopyOnWriteArraySet<Integer> initMethodWarnCode(String methodName) {
        CopyOnWriteArraySet<Integer> errorCodes = new CopyOnWriteArraySet<>();
        partErrorCode.forEach((key, value) -> {
            if (methodName.contains(key.toLowerCase())) {
                errorCodes.addAll(value);
            }
        });
        return errorCodes;
    }

    public synchronized void setPartErrorCode(Map<String, Collection<Integer>> partErrorCode) {
        this.partErrorCode = partErrorCode;
        int oldSize = methodWarnCode.size();
        //刷新缓存
        methodWarnCode = new ConcurrentHashMap<>(oldSize);

    }

    public void setGlobalWarnCode(Collection<Integer> globalWarnCode) {
        this.globalWarnCode = new HashSet<>(globalWarnCode);
    }


    static class Local {
        volatile boolean process;
    }

}