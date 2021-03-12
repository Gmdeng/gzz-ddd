package com.gzz.retail.infra.defines.state;

/**
 * 退货状态
 */
public enum ReturnStatus {
    ADD (0, "新建") ,
    PACKAGE (2, "打包"),
    DELIVER ( 4, "交付"),
    CANCEL ( 8, "取消"),
    FINISH (16, "成功") ;

    private int key;
    private String name;


    ReturnStatus(int key, String name){
        this.key = key;
        this.name = name;
    }

    public int value(){return this.key;}
    public String label() {return  this.name;}

    public static ReturnStatus valueOf(int value){
        for (ReturnStatus status: ReturnStatus.values()) {
            if(status.value() == value) return status;
        }
        return ADD;
    }
}
