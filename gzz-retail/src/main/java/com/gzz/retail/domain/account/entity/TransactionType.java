package com.gzz.retail.domain.account.entity;

/**
 * 交易类型
 */
public enum TransactionType {
    DEBIT(1, "取出"),
    CREDIT(2, "存入"),
    TRANSFER_TO(3, "转入"),
    TRANSFER_FROM(4, "存入"),
    FROZEN(5, "冻结"),
    UN_FROZEN(6, "解冻");
    private int code;
    private String desc;

    TransactionType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer code() {
        return this.code;
    }

    public String desc() {
        return this.desc;
    }
}
