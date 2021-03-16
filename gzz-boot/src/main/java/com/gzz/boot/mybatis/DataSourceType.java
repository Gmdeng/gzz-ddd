package com.gzz.boot.mybatis;

/**
 * 数据源类型
 */
public enum DataSourceType {
    MASTER("master"),
    SLAVE1("slave"),
    SLAVE2("slave"),
    READ("slave"),
    WRITE("slave");

    private String name;

    DataSourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
