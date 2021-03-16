package com.gzz.boot.mybatis;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过ThreadLocal将数据源设置到每个线程上下文中
 *
 * @author G-m
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();
    //private static ThreadLocal<Object> contextHolder = ThreadLocal.withInitial(() -> DataSourceType.MASTER.getName());

    private static final AtomicInteger counter = new AtomicInteger(-1);

    // 获取数据源名
    public static DataSourceType getDataSourceKey() {
        return contextHolder.get();
    }

    // 设置数据源名
    public static void setDataSourceKey(DataSourceType dbType) {
        contextHolder.set(dbType);
    }

    // 清除数据源名
    public static void clearDataSourceKey() {
        contextHolder.remove();
    }

    public static void Read() {
        setDataSourceKey(DataSourceType.READ);
        //System.out.println("切换到master");
    }

    public static void Write() {
        setDataSourceKey(DataSourceType.WRITE);
        //System.out.println("切换到slave1");
    }

    public static void Master() {
        setDataSourceKey(DataSourceType.MASTER);
        //System.out.println("切换到master");
    }

    public static void Slave() {
        // 轮询
        int index = counter.getAndIncrement() % 2;
        if (counter.get() > 9999) {
            counter.set(-1);
        }
        if (index == 0) {
            setDataSourceKey(DataSourceType.SLAVE1);
            //System.out.println("切换到slave1");
        } else {
            setDataSourceKey(DataSourceType.SLAVE2);
            //System.out.println("切换到slave2");
        }
    }
}
