package com.gzz.retail.infra.defines;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 通用记录状态
 */
public enum CommStatus {
    ADD (0, "新增") ,
    AUDIT ( 1, "审核"),
    UNAUDIT ( 2 , "待审"),
    VIEW (-1, "已删") ;

    private int key;
    private String name;


    CommStatus(int key, String name){
        this.key = key;
        this.name = name;
    }

    public int value(){return this.key;}
    public String label() {return  this.name;}

    public static Optional<CommStatus> valueOf(int value){
        return Stream.of(values()).filter(x-> x.key == value).findFirst();
    }
}
