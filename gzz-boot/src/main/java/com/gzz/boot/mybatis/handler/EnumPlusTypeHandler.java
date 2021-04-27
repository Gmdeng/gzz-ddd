package com.gzz.boot.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(value = JdbcType.TINYINT, includeNullJdbcType = true)
public class EnumPlusTypeHandler extends BaseTypeHandler<IEnumPlus> {
    private Class<IEnumPlus> type;

    public EnumPlusTypeHandler(){};

    public EnumPlusTypeHandler(Class<IEnumPlus> type) {
        if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IEnumPlus parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getKey());
    }

    @Override
    public IEnumPlus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getInt(columnName));
    }

    @Override
    public IEnumPlus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getInt(columnIndex));
    }

    @Override
    public IEnumPlus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getInt(columnIndex));
    }

    private IEnumPlus convert(int status){
        IEnumPlus[] objs = type.getEnumConstants();
        for(IEnumPlus em: objs){
            if(em.getKey() == status){
                return  em;
            }
        }
        return null;
    }
}
