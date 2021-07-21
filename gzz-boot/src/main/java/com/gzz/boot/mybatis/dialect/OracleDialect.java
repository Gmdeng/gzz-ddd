package com.gzz.boot.mybatis.dialect;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.RowBounds;

/**
 *  Oracle方言
 */
public class OracleDialect implements IDialect {
    @Override
    public void afterAll() {
    }

    @Override
    public String getPagedSql(BoundSql boundSql, RowBounds rowBounds) {
        String sql = boundSql.getSql();
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        sb.append(" LIMIT ");
        sb.append(rowBounds.getOffset());
        sb.append(",");
        sb.append(rowBounds.getLimit());
        return sb.toString();
    }
}