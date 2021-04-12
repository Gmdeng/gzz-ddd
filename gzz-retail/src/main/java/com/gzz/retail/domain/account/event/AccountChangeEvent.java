package com.gzz.retail.domain.account.event;

import com.gzz.core.model.AbstractDomainEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AccountChangeEvent extends AbstractDomainEvent {
    private String origin;
    private String target;
}
