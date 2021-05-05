package com.demo.dao.handlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class MyTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> list, JdbcType jdbcType)
            throws SQLException {
        String hobbys =list.stream().map(String::valueOf).collect(Collectors.joining(","));
        //String hobbys = String.join(",", list.stream().map(String::valueOf).collect(Collectors.toList()));
//        //对占位符进行处理
//        StringBuilder sb = new StringBuilder();
//        for (String hobby : list) {
//            sb.append(hobby);
//            sb.append(";");
//        }
//        String hobbys = sb.toString();
        //hobbys = hobbys.substring(0, hobbys.length()-1);
        //为占位符设值
        ps.setString(i, hobbys);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String hobby = rs.getString(columnName);
        if (hobby == null) {
            return null;
        }
        String[] split = hobby.split(",");
        return Arrays.asList(split);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String hobby = rs.getString(columnIndex);
        if (hobby == null) {
            return null;
        }
        String[] split = hobby.split(",");
        return Arrays.asList(split);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String hobby = cs.getString(columnIndex);
        if (hobby == null) {
            return null;
        }
        String[] split = hobby.split(",");
        return Arrays.asList(split);
    }

}