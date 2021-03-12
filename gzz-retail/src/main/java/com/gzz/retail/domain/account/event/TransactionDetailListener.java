package com.gzz.retail.domain.account.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class TransactionDetailListener {
    /**
     * 监听交易明细事件
     *
     * @param event
     */
    @EventListener
    public void CreateTransactionDetail(TransactionDetailEvent event) {
        log.info("changeItemSales" + event.getOrigin() + " | " + event.getTarget());
    }
}
