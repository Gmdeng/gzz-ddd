package com.gzz.retail.infra.defines.state;
/**
 * 商品销售状态
 */
public enum SaleStatus {
    WAIT(0, "待上"),
    ON (1, "在售") ,
    OFF ( 2, "下架");


    private int key;
    private String name;

    SaleStatus(int key, String name){
        this.key = key;
        this.name = name;
    }

    public int value(){return this.key;}
    public String label() {return  this.name;}

    public static SaleStatus valueOf(int value){
        for (SaleStatus status: SaleStatus.values()) {
            if(status.value() == value) return status;
        }
        return ON;
    }
}
