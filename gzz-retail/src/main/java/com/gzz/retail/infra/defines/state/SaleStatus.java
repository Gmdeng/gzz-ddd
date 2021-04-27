package com.gzz.retail.infra.defines.state;

import com.gzz.boot.mybatis.handler.IEnumPlus;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 商品销售状态
 */
public enum SaleStatus implements IEnumPlus {
    WAIT(0, "待上"),
    ON(1, "在售"),
    OFF(2, "下架");


    String label;
    Integer key;

    SaleStatus(Integer key, String label) {
        this.key = key;
        this.label = label;
    }

//    public static SaleStatus valueOf(int value) {
//        for (SaleStatus status : SaleStatus.values()) {
//            if (status.key == value) return status;
//        }
//        return ON;
//    }

    public static Optional<SaleStatus> valueOf(int value) {
        return Stream.of(values()).filter(x -> x.key == value).findFirst();
    }

}
