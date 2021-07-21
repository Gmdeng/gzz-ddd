package com.gzz.boot.mybatis.dialect;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.RowBounds;

/**
 * 数据库方言
 */
public interface IDialect {
    void afterAll();
    String getPagedSql(BoundSql boundSql, RowBounds rowBounds);
}
