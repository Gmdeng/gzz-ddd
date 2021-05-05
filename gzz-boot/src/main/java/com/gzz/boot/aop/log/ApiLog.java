package com.gzz.boot.aop.log;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLog {
    /**
     * 接口名称
     */
    String serviceName();
    /**
     * 接口类型(HTTP,WEBSERVICE)
     * 默认HTTP
     */
    String serviceType() default "HTTP";
    /**
     * 传输类型(RECEIVE:接收，SEND:发送)
     */
    String type() default "SEND";

    /**
     * 单据类型
     */
    String billType() default "";

    /**
     * 目标系统
     */
    String targetSys() default "";

    /**
     * 访问地址
     */
    String url() default "";

    /**
     * 回调检查结果方法类
     * return
     */
    //Class<? extends LogWriteBack> aClass();
}
