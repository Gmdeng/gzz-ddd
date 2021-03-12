package com.gzz.boot.event.impl.memory;

import com.alibaba.fastjson.JSON;
import com.gzz.boot.event.IStoreEventHandle;
import com.gzz.boot.event.StoredEvent;
import com.gzz.core.model.IDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现存储事件
 */
public class MemoryStoreEventHandle implements IStoreEventHandle {
    private List<StoredEvent> storedEvents;

    public MemoryStoreEventHandle() {
        this.storedEvents = new ArrayList<StoredEvent>();
    }

    @Override
    public List<StoredEvent> allStoredEventsBetween(long lowStoredEventId, long highStoredEventId) {
        List<StoredEvent> events = new ArrayList<StoredEvent>();
        for (StoredEvent storedEvent : this.storedEvents) {
            if (storedEvent.eventId() >= lowStoredEventId && storedEvent.eventId() <= highStoredEventId) {
                events.add(storedEvent);
            }
        }
        return events;
    }

    @Override
    public List<StoredEvent> allStoredEventsSince(long storedEventId) {
        List<StoredEvent> events = new ArrayList<StoredEvent>();
        for (StoredEvent storedEvent : this.storedEvents) {
            if (storedEvent.eventId() > storedEventId) {
                events.add(storedEvent);
            }
        }
        return events;
    }

    @Override
    public synchronized StoredEvent append(IDomainEvent domainEvent) {
        String eventSerialization = JSON.toJSONString(domainEvent);
        StoredEvent storedEvent = new StoredEvent(this.storedEvents.size() + 1,
                eventSerialization,
                domainEvent.getClass().getName(),
                domainEvent.getOccurredOn());
        this.storedEvents.add(storedEvent);
        return storedEvent;
    }

    @Override
    public void close() {
        this.clean();
    }

    @Override
    public long countStoredEvents() {
        return this.storedEvents.size();
    }

    public void clean() {
        this.storedEvents = new ArrayList<StoredEvent>();
    }
}
