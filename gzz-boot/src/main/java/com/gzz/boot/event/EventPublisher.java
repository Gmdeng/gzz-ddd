package com.gzz.boot.event;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 事件发布器
 */
@Slf4j
public class EventPublisher implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static <T> void publish(final T domainEvent) {
        if (applicationContext != null) {
            applicationContext.publishEvent(domainEvent);
        } else {
            log.warn("publish domain event fail because ApplicationContext null, event={}", JSON.toJSONString(domainEvent));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        EventPublisher.applicationContext = applicationContext;
    }
}

