package com.gzz.retail.domain.order;

import com.gzz.core.util.IdGenerator;
import com.gzz.retail.domain.member.model.Member;
import com.gzz.retail.domain.order.model.SaleOrder;
import com.gzz.retail.infra.defines.state.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 销售订单工厂
 */
@Component
public class OrderFactory {
    @Autowired
    private IdGenerator idGenerator;

    /**
     * 创建销售订单
     * @return
     */
    public SaleOrder buildSaleOrder(){
        SaleOrder order = new SaleOrder();
        order.no(idGenerator.generateNo("GM"))
                .timeOn(new Date())
                .modeOfPayment(0)
                .status(OrderStatus.ADD);
        return order;
    }

    /**
     *
     * @param m
     * @return
     */
    public SaleOrder build(Member m) {
        return buildSaleOrder().memberNo(m.getNo()).memberName(m.getName());
    }
}
