package com.gzz.boot.event;

import com.alibaba.fastjson.JSON;
import com.gzz.core.model.IDomainEvent;

import java.util.Date;

/**
 *
 */
public class StoredEvent {
    private String eventBody;
    private long eventId;
    private Date occurredOn;
    private String typeName;

    public StoredEvent() {
        this.setEventId(-1);
    }

    public StoredEvent(String eventBody, String typeName, Date occurredOn) {
        this.eventBody = eventBody;
        this.occurredOn = occurredOn;
        this.typeName = typeName;
    }

    public StoredEvent(long eventId, String eventBody, String typeName, Date occurredOn) {
        this.eventBody = eventBody;
        this.eventId = eventId;
        this.occurredOn = occurredOn;
        this.typeName = typeName;
    }

    @SuppressWarnings("unchecked")
    public <T extends IDomainEvent> T toDomainEvent() {
        Class<T> domainEventClass = null;
        try {
            domainEventClass = (Class<T>) Class.forName(this.typeName());
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Class load error, because: "
                            + e.getMessage());
        }
        T domainEvent = JSON.parseObject(this.eventBody(), domainEventClass);
        return domainEvent;
    }

    public String typeName() {
        return this.typeName;
    }

    public String eventBody() {
        return this.eventBody;
    }

    public long eventId() {
        return this.eventId;
    }

    public Date occurredOn() {
        return this.occurredOn;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            StoredEvent typedObject = (StoredEvent) anObject;
            equalObjects = this.eventId() == typedObject.eventId();
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                +(1237 * 233)
                        + (int) this.eventId();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "StoredEvent [eventBody=" + eventBody + ", eventId=" + eventId + ", occurredOn=" + occurredOn + ", typeName="
                + typeName + "]";
    }

    protected void setEventBody(String anEventBody) {
        this.eventBody = anEventBody;
    }

    protected void setEventId(long anEventId) {
        this.eventId = anEventId;
    }

    protected void setOccurredOn(Date anOccurredOn) {
        this.occurredOn = anOccurredOn;
    }

    protected void setTypeName(String aTypeName) {
        this.typeName = aTypeName;
    }
}
