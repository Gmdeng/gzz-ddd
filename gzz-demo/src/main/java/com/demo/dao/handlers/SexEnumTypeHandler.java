package com.demo.dao.handlers;

import com.demo.entity.SexTypeEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.TINYINT)
@MappedTypes(SexTypeEnum.class)
public class SexEnumTypeHandler implements TypeHandler<SexTypeEnum> {
    @Override
    public void setParameter(PreparedStatement ps, int i, SexTypeEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public SexTypeEnum getResult(ResultSet rs, String columnName) throws SQLException {
        return SexTypeEnum.valueOf(rs.getInt(columnName));
    }

    @Override
    public SexTypeEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
        return SexTypeEnum.valueOf(rs.getInt(columnIndex));
    }

    @Override
    public SexTypeEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return SexTypeEnum.valueOf(cs.getInt(columnIndex));
    }
}
