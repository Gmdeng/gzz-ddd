package com.gzz.boot.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 自定义数据类型转换
 *
 * 适合用于long保存日期
 * 日期转UNIX_TIMESTAMP(数字)类型转换处理
 * @param
 */
@MappedTypes(Date.class)
@MappedJdbcTypes(JdbcType.BIGINT)
public class CustomDateTypeHandler extends BaseTypeHandler<Date> {

    /**
     * 设置非空参数
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getTime());
    }

    /**
     *  根据列名，获取可以为空的结果
     */
    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Long sqlTimetamp = rs.getLong(columnName);
        if (null != sqlTimetamp){
            return new Date(sqlTimetamp);
        }
        return null;
    }
    /**
     *  根据列名，获取可以为空的结果
     */
    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Long sqlTimetamp = rs.getLong(columnIndex);
        if (null != sqlTimetamp){
            return new Date(sqlTimetamp);
        }
        return null;
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Long sqlTimetamp = cs.getLong(columnIndex);
        if (null != sqlTimetamp){
            return new Date(sqlTimetamp);
        }
        return null;
    }
}
