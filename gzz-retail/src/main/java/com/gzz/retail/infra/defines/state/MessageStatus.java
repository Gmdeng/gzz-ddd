package com.gzz.retail.infra.defines.state;

import com.gzz.boot.mybatis.handler.IEnumPlus;

import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 */
public enum MessageStatus implements IEnumPlus {
    AWAIT_RETRY(1, "AWAIT_RETRY"), // 等待重试
    ARE_RETRY(2, "ARE_RETRY"), // 准备重试
    RETRY_QUIT(3, "RETRY_QUIT");// 重试退出

    String label;
    Integer key;

    MessageStatus(Integer key, String label) {
        this.key = key;
        this.label = label;
    }

    public static Optional<MessageStatus> valueOf(int value) {
        return Stream.of(values()).filter(x -> x.key == value).findFirst();
    }
}
