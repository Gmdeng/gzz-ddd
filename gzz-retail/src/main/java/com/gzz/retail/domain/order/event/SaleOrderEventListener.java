package com.gzz.retail.domain.order.event;

import com.gzz.retail.domain.account.event.TransactionDetailEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SaleOrderEventListener {
    /**
     *  订单创建事件
     *  1，发信息给客户
     *  2，记录订单状态日志
     * @param event
     */
    @EventListener
    public void SaleOrderCreateEventHandle(SaleOrderEventCreate event) {
        log.info("changeItemSales");
    }

}
