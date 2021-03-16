package com.gzz.retail.infra.defines.types;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 操作类型
 */
public enum OperateType {
    VIEW(1, "查看"),
    ADD(2, "增加"),
    MODIFY(4, "修改"),
    DELETE(8, "删除"),
    CANCEL(16, "取消"),
    AUDIT(32, "审核"),
    PASS(64, "通过"),
    REJECT(128, "拒绝");


    private int key;
    private String name;


    OperateType(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public static Optional<OperateType> valueOf(int value) {
        return Stream.of(values()).filter(x -> x.key == value).findFirst();
    }

    public int value() {
        return this.key;
    }

    public String label() {
        return this.name;
    }
}
