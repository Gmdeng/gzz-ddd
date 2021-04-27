package com.gzz.retail.infra.defines.state;

import com.gzz.boot.mybatis.handler.IEnumPlus;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 退货状态
 */
public enum ReturnStatus implements IEnumPlus {
    ADD(0, "新建"),
    PACKAGE(2, "打包"),
    DELIVER(4, "交付"),
    CANCEL(8, "取消"),
    FINISH(16, "成功");

    String label;
    Integer key;

    ReturnStatus(Integer key, String label) {
        this.key = key;
        this.label = label;
    }

    //    public static ReturnStatus valueOf(int value) {
//        for (ReturnStatus status : ReturnStatus.values()) {
//            if (status.key == value) return status;
//        }
//        return ADD;
//    }
    public static Optional<ReturnStatus> valueOf(int value) {
        return Stream.of(values()).filter(x -> x.key == value).findFirst();
    }
}
