package com.gzz.retail.domain.payment;

/**
 * 支付状态
 */
public enum PaymentStatus {
    ADD(0, "新建"),
    WAIT(1, "待付"),
    FINISH(2, "完成"),
    SUCCESS(3, "成功"),
    FAIL(4, "失败");

    private int key;
    private String name;

    PaymentStatus(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public static PaymentStatus valueOf(int value) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.value() == value) return status;
        }
        return ADD;
    }

    public int value() {
        return this.key;
    }

    public String label() {
        return this.name;
    }
}
