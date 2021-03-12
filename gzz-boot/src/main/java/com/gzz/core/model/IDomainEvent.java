package com.gzz.core.model;

import java.util.Date;

public interface IDomainEvent {

    default int getEventVersion() {
        return eventVersion();
    }

    default Date getOccurredOn() {
        return occurredOn();
    }

    default int eventVersion() {
        return getEventVersion();
    }

    default Date occurredOn() {
        return getOccurredOn();
    }
}
