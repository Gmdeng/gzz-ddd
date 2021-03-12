package com.gzz.boot.event.impl.mybatis;

import com.gzz.boot.event.IStoreEventHandle;
import com.gzz.boot.event.StoredEvent;
import com.gzz.core.model.IDomainEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 实现存储事件
 */
public class MybatisStoreEventHandle implements IStoreEventHandle {
    @Autowired
    private StoreEventMapper storeEventMapper;

    @Override
    public List<StoredEvent> allStoredEventsBetween(long lowStoredEventId, long highStoredEventId) {
        return storeEventMapper.allStoredEventsBetween(lowStoredEventId, highStoredEventId);
    }

    @Override
    public List<StoredEvent> allStoredEventsSince(long storedEventId) {
        return storeEventMapper.allStoredEventsSince(storedEventId);
    }

    @Override
    public StoredEvent append(IDomainEvent domainEvent) {
        String eventSerialization = ""; //json.toJson(aDomainEvent);
        StoredEvent storedEvent = new StoredEvent(eventSerialization, domainEvent.getClass().getName(), domainEvent.getOccurredOn());
        storeEventMapper.create(storedEvent);
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public long countStoredEvents() {
        return storeEventMapper.countStoredEvents();
    }
}
