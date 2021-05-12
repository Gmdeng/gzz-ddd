package com.gzz.retail.infra.defines.types;

import com.alibaba.fastjson.annotation.JSONType;
import com.gzz.boot.mybatis.handler.IEnumPlus;
import com.gzz.core.enumtype.EnumDeserializer;
import com.gzz.core.enumtype.EnumSerializer;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 操作类型
 */

public enum OperateType implements IEnumPlus {

    VIEW(1, "查看"),
    ADD(2, "增加"),
    MODIFY(4, "修改"),
    DELETE(8, "删除"),
    CANCEL(16, "取消"),
    AUDIT(32, "审核"),
    PASS(64, "通过"),
    REJECT(128, "拒绝");

    String label;
    Integer key;

    OperateType(int key, String label) {
        this.key = key;
        this.label = label;
    }

    public static Optional<OperateType> valueOf(int value) {
        return Stream.of(values()).filter(x -> x.key == value).findFirst();
    }
}
