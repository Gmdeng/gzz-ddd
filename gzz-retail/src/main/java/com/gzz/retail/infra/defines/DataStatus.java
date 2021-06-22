package com.gzz.retail.infra.defines;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 数据状态
 */
public enum DataStatus {
    ENABLED(1, "启用"), DISABLE(2, "禁用");

    int value;
    String lable;
    DataStatus(int value, String label){
        this.value = value;
        this.lable = label;
    }

    public static Optional<DataStatus> valueOf(int val){
        return Stream.of(values()).filter(x -> x.value == val).findFirst();
    }
}
