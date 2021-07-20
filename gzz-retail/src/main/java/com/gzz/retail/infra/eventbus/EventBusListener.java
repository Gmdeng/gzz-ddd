package com.gzz.retail.infra.eventbus;

import com.google.common.eventbus.EventBus;
import org.apache.shiro.event.Subscribe;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 事件总线监听
 * 事件监听器，可以监听多个事件。处理方法添加 @Subscribe 注解即可。
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
     * 监听 HelloEvent 类型及其父类型（Object）的事件
     */
    @Subscribe
    public void processEvent(DemoEvent event){
        System.out.println("process hello event, name:" + event.getEventNo());
    }
    /**
     * 监听 HelloEvent 类型及其父类型（Object）的事件
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
        EventBus eventBus = new EventBus();
        // 注册监听
        eventBus.register(new EventBusListener());
        // 发布事件
        eventBus.post(new DemoEvent());
    }
}
