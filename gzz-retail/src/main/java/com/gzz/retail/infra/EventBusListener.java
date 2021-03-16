package com.gzz.retail.infra;

import org.apache.shiro.event.Subscribe;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EventBusListener {

    @PostConstruct
    public void init() {

    }

    @Subscribe
    public void tranfer() {

    }
}
