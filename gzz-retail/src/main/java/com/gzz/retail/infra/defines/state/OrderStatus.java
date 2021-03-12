package com.gzz.retail.infra.defines.state;

/**
 * 订单状态
 */
public enum OrderStatus {
    ADD (0, "新建") ,
    PAYED ( 2, "待发货"),
    SHIPED ( 4, "待收货"),
    CANCEL ( 8, "取消"),
    RETURN ( 16, "退货"),
    FINISH (32, "完成") ;


    private int key;
    private String name;

    OrderStatus(int key, String name){
        this.key = key;
        this.name = name;
    }

    public int value(){return this.key;}
    public String label() {return  this.name;}

    public static OrderStatus valueOf(int value){
        for (OrderStatus status: OrderStatus.values()) {
            if(status.value() == value) return status;
        }
        return ADD;
    }
}
