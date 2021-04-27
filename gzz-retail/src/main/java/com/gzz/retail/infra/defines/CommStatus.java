package com.gzz.retail.infra.defines;

import com.gzz.boot.mybatis.handler.IEnumPlus;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 通用记录状态
 */
public enum CommStatus implements IEnumPlus {
    ADD(0, "新增"),
    AUDIT(1, "审核"),
    UNAUDIT(2, "待审"),
    DELETE(-1, "已删");


    String label;
    Integer key;

    CommStatus(Integer key, String label) {
        this.key = key;
        this.label = label;
    }

    public static Optional<CommStatus> valueOf(int value) {
        return Stream.of(values()).filter(x -> x.key == value).findFirst();
    }

}
