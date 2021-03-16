package com.gzz.retail.infra.defines.state;

/**
 * 发货单状态
 */
public enum ShipStatus {
    ADD(0, "新建"),
    PACKAGE(2, "打包"),
    DELIVER(4, "交付"),
    CANCEL(8, "取消"),
    FINISH(16, "成功");

    private int key;
    private String name;


    ShipStatus(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public static ShipStatus valueOf(int value) {
        for (ShipStatus status : ShipStatus.values()) {
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
