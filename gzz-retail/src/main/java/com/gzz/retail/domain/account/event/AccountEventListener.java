package com.gzz.retail.domain.account.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 观察者模式
 *
 * @EntityListeners
 * @DomainEvents可以返回单个事件实例或事件集合,
 * @DomainEvents用来发布时间，触发机制在保存的时候。批量保存对象时，每个对象都会触发一次事件
 * @AfterDomainEventPublication在事件发布之后触发。批量保存时触发多次
 *
 * @DomainEvents和@TransactionalEventListener的组合使用，给我们处理实体保存后触发事件。
 * 特别是异步事件（给event方法加上@Async，同时开启@EnableAsync）是非常简便的，它是一种领域驱动的思想，让代码显得更加的内聚。
 *
 */
@Component
@Slf4j
public class AccountEventListener {

    /**
     * 实现事务监控
     *
     * @param event
     * @TransactionalEventListener注解的phase有多个选项
     *
     *     BEFORE_COMMIT
     *     AFTER_COMMIT  即事务提交后响应事件
     *     AFTER_ROLLBACK
     *     AFTER_COMPLETION
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void changeItemSales(AccountChangeEvent event) {
        log.info("");
        log.info("changeItemSales [{}]| [{}] ",  event.getOrigin() , event.getTarget());
    }

    /**
     * 监控
     * @param event
     */
    @EventListener
    public void changeSkuSellCount(AccountUpdateEvent event) {
        log.info("changeSkuSellCount[{}]| [{}]| [{}]| [{}]", event.getOrigin(), event.getTarget(), event.getEventVersion(), event.getOccurredOn());
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //
                log.info("afterCommit...........................");
            }
        });
    }
}
