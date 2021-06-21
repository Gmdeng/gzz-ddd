package com.gzz.boot.mybatis.handler;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * 对所有查询结果中的类型为String 的属性做一个HTML Decode处理
 */
@MappedTypes(String.class) //注解来指定与其关联的 Java 类型列表
//  注解来指定与其关联的 JDBC 类型列表。 如果在 jdbcType 属性中也同时指定，则注解方式将被忽略。
@MappedJdbcTypes(value = {JdbcType.CLOB, JdbcType.CLOB, JdbcType.VARCHAR, JdbcType.LONGVARCHAR, JdbcType.NVARCHAR, JdbcType.NCHAR, JdbcType.NCLOB}, includeNullJdbcType = true)
public class GlobalHTMLTypeHandler extends BaseTypeHandler<String> {

    /**
     * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型
     * @param ps 当前的PreparedStatement对象
     * @param i 当前参数的位置
     * @param parameter 当前参数的Java对象
     * @param jdbcType 当前参数的数据库类型
     * @throws SQLException
     */

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, StringEscapeUtils.escapeHtml4(parameter));
    }

    /**
     * 用于在Mybatis获取数据结果集时如何把数据库类型转换为对应的Java类型
     * @param rs 当前的结果集
     * @param columnName 当前的字段名称
     * @return 转换后的Java对象
     * @throws SQLException

     */
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return StringEscapeUtils.unescapeHtml4(rs.getString(columnName));
    }

    /**
     * 用于在Mybatis通过字段位置获取字段数据时把数据库类型转换为对应的Java类型
     * @param rs 当前的结果集
     * @param columnIndex 当前字段的位置
     * @return 转换后的Java对象
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return StringEscapeUtils.unescapeHtml4(rs.getString(columnIndex));
    }

    /**
     * 用于Mybatis在调用存储过程后把数据库类型的数据转换为对应的Java类型
     * @param cs 当前的CallableStatement执行后的CallableStatement
     * @param columnIndex 当前输出参数的位置
     * @return
     * @throws SQLException
     */
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return StringEscapeUtils.unescapeHtml4(cs.getString(columnIndex));
    }
}
