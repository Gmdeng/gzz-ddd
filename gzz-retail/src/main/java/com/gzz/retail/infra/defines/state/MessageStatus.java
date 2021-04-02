package com.gzz.retail.infra.defines.state;

public enum MessageStatus {
    AWAIT_RETRY(1, "AWAIT_RETRY"), // 等待重试
    ARE_RETRY(2, "ARE_RETRY"), // 准备重试
    RETRY_QUIT(3, "RETRY_QUIT");// 重试退出

    private Integer id;

    private String name;

    MessageStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
