package com.gzz.retail.domain.account.event;

import com.gzz.core.model.DomainEvent;
import com.gzz.retail.domain.account.model.TransactionDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 交易明细事件
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TransactionDetailEvent extends DomainEvent {
    private String origin;
    private String target;
    private TransactionDetail detail;
}
