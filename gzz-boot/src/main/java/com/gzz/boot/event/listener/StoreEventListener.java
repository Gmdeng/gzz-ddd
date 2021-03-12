package com.gzz.boot.event.listener;

import com.gzz.boot.event.IStoreEventHandle;
import com.gzz.core.model.IDomainEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

@Slf4j
public class StoreEventListener {
    @Autowired
    @Setter
    private IStoreEventHandle storeEvent;

    @EventListener
    public void storeEvents(IDomainEvent domainEvent) {
        System.out.println(domainEvent.getClass().getSimpleName());
        //log.info("store event: {} = {}", domainEvent.getClass().getSimpleName(), DomainRegistry.service(Json.class).toJson(domainEvent));
        storeEvent.append(domainEvent);
    }
}
