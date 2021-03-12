package com.gzz.boot.aop.exception;

import com.alibaba.fastjson.JSON;
import com.gzz.boot.aop.log.VisitLog;
import com.gzz.core.util.AopUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AOP 统一异常
 * AOP相关概念：Aspect、Advice、Join point、Pointcut、Weaving、Target等。
 * 相关注解：@Aspect、@Pointcut、@Before、@Around、@After、@AfterReturning、@AfterThrowing
 * Pointcut定义时，还可以使用&&、||、! 这三个运算
 * Pointcut格式：
 * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)
 *各个pattern分别表示：
 *
 * 修饰符匹配（modifier-pattern?）
 * 返回值匹配（ret-type-pattern）可以为*表示任何返回值,全路径的类名等
 * 类路径匹配（declaring-type-pattern?）
 * 方法名匹配（name-pattern）可以指定方法名 或者 *代表所有, set* 代表以set开头的所有方法
 * 参数匹配（(param-pattern)）可以指定具体的参数类型，多个参数间用“,”隔开，各个参数也可以用“*”来表示匹配任意类型的参数，如(String)表示匹配一个String参数的方法；(*,String) 表示匹配有两个参数的方法，第一个参数可以是任意类型，而第二个参数是String类型；可以用(..)表示零个或多个任意参数
 * 异常类型匹配（throws-pattern?）
 * 其中后面跟着“?”的是可选项
 */
@Slf4j
@Aspect
public class ExceptionAspect {
    private List<IExceptionProcess> DEFAULT_EXCEPTION_PROCESS = new ArrayList<>();
    private List<IExceptionProcess> exceptionProcess;

    /**
     * 初始化后不应该在修改，所以不存在多线程问题
     */
    private Map<Class<?>, IExceptionProcess> map = new HashMap<>();

    /**
     * 服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Serclet的inti()方法
     */
    @PostConstruct
    public void init(){
        for (IExceptionProcess process : DEFAULT_EXCEPTION_PROCESS) {
            map.put(process.processClass(), process);
        }

        if (exceptionProcess!=null){
            for (IExceptionProcess process : exceptionProcess) {
                map.put(process.processClass(),process);
            }
        }
    }

    public void setUserExceptionProcess(
            List<IExceptionProcess> exceptionProcess) {
        this.exceptionProcess = exceptionProcess;
    }
    //连接点是@RequestMapping注解的方法
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping)")
    private void RequestMappingPointcut() {}



    //抛出的异常在这边捕获
    @AfterThrowing(pointcut = "RequestMappingPointcut()", throwing = "e")
    public void handleThrowing(JoinPoint joinPoint, Exception e) {
        log.error("抛出的异常在这统一捕获处理....");
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        //开始打log
        log.error("异常:                   " + e.getMessage());
        log.error("异常getClass:           " + e.getClass());
        log.error("异常getLocalizedMessage:" + e.getLocalizedMessage());
        log.error("异常getStackTrace:      " + e.getStackTrace());
        log.error("异常所在类：             " + className);
        log.error("异常所在方法：           " + methodName);
        log.error("异常中的参数：           " + JSON.toJSONString(args));
    }



    // 自定义异常处理
    @Around("@annotation(com.gzz.boot.aop.exception.CustomException)||" +
            "@within(com.gzz.boot.aop.exception.CustomException)")
    public Object doCustomException(ProceedingJoinPoint pjp) throws Throwable {
        return processException(pjp);
    }

    /**
     * 处理异常
     */
    private Object processException(ProceedingJoinPoint pjp) throws Throwable {
        Method method = AopUtil.getMethod(pjp);
        Class<?> returnType=null;
        if (method!=null){
            returnType = method.getReturnType();
        }

        CustomException annotation = AopUtil.getAnnotationPresent(pjp, CustomException.class);
        if (annotation != null) {
            try {
                return pjp.proceed();
            } catch (Throwable e) {
                Object result;
                if (returnType!=null){
                    IExceptionProcess exceptionProcess = map.get(returnType);
                    if (exceptionProcess==null){
                        throw  e;
                    }
                    result = exceptionProcess.processException(e);
                    if (annotation.printError()) {
                        log.error("处理异常 result="+result.toString(),e);
                    }
                    return result;

                }
                throw  e;
            }
        } else {
            return pjp.proceed();
        }

    }
}
