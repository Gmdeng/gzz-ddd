package com.gzz.retail.domain.order;

import com.gzz.core.util.IdGenerator;
import com.gzz.retail.domain.member.model.MemberDo;
import com.gzz.retail.domain.order.model.SaleOrderDo;
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
     *
     * @return
     */
    public SaleOrderDo buildSaleOrder() {
        SaleOrderDo order = new SaleOrderDo();
        order.no(idGenerator.generateNo("GM"))
                .timeOn(new Date())
                .modeOfPayment(0)
                .status(OrderStatus.ADD);
        return order;
    }

    /**
     * @param m
     * @return
     */
    public SaleOrderDo build(MemberDo m) {
        return buildSaleOrder().memberNo(m.getNo()).memberName(m.getName());
    }
}
