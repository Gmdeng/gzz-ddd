package com.gzz.core.model;

import java.util.Date;

/**
 * 领域事件抽像
 */
public abstract class DomainEvent implements IDomainEvent {
    // 事件版本
    protected int eventVersion = 1;
    // 事件发生时间
    protected Date OccurredOn = new Date();
}
