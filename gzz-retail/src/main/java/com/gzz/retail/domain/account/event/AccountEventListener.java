package com.gzz.retail.domain.account.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 观察者模式
 */
@Component
@Slf4j
public class AccountEventListener {

    /**
     * 实现事务监控
     *
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void changeItemSales(AccountChangeEvent event) {
        log.info("");
        log.info("changeItemSales" + event.getOrigin() + " | " + event.getTarget());
    }

    @EventListener
    public void changeSkuSellCount(AccountUpdateEvent event) {
        log.info("changeSkuSellCount" + event.getOrigin() + " | " + event.getTarget() + " | " + event.getEventVersion() + " | " + event.getOccurredOn());
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //
                log.info("afterCommit...........................");
            }
        });
    }
}
