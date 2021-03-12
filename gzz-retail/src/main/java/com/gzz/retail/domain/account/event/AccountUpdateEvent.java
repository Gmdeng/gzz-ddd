package com.gzz.retail.domain.account.event;

import com.gzz.core.model.DomainEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AccountUpdateEvent extends DomainEvent {
    private String origin;
    private String target;
}

