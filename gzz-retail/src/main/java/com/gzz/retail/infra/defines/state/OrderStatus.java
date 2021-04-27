package com.gzz.retail.infra.defines.state;

import com.gzz.boot.mybatis.handler.IEnumPlus;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 订单状态
 */
public enum OrderStatus implements IEnumPlus {
    ADD(0, "新建"),
    PAYED(2, "待发货"),
    SHIPED(4, "待收货"),
    CANCEL(8, "取消"),
    RETURN(16, "退货"),
    FINISH(32, "完成");

    String label;
    Integer key;

    OrderStatus(Integer key, String label) {
        this.key = key;
        this.label = label;
    }

    //    public static OrderStatus valueOf(int value) {
//        for (OrderStatus status : OrderStatus.values()) {
//            if (status.key == value) return status;
//        }
//        return ADD;
//    }
    public static Optional<OrderStatus> valueOf(int value) {
        return Stream.of(values()).filter(x -> x.key == value).findFirst();
    }

}
