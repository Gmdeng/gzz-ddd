package com.gzz.retail.infra;

import org.apache.shiro.event.Subscribe;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 事件总线监听
 *
 */
@Component
public class EventBusListener {

    /**
     * 构造方法执行后，初始化
     * 在服务器加载Servlet(bean)的时候运行，并且只会被服务器调用一次
     */
    @PostConstruct
    public void init() {

    }

    /**
     * 转发
     */
    @Subscribe
    public void tranfer() {

    }

    /**
     * 注解在容器销毁
     * 在服务器卸载Servlet(bean)的时候运行，并且只会被服务器调用一次
     */
    @PreDestroy
    public void destroy(){

    }
}
