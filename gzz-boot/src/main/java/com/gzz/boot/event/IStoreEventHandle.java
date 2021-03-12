package com.gzz.boot.event;

import com.gzz.core.model.IDomainEvent;

import java.util.List;

public interface IStoreEventHandle {

    public List<StoredEvent> allStoredEventsBetween(long lowStoredEventId, long highStoredEventId);

    public List<StoredEvent> allStoredEventsSince(long storedEventId);

    public StoredEvent append(IDomainEvent domainEvent);

    public void close();

    public long countStoredEvents();
}
