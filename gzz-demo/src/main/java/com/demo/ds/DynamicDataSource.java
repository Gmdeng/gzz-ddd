package com.demo.ds;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * 动态数据源（需要继承AbstractRoutingDataSource）
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    //定义一个线程安全的DatabaseType容器
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<DatabaseType>();

    public static DatabaseType getDatabaseType(){
        return contextHolder.get();
    }

    public static void setDatabaseType(DatabaseType type) {
        contextHolder.set(type);
    }
    //获取当前线程的DatabaseType
    protected Object determineCurrentLookupKey() {
        return getDatabaseType();
    }
}