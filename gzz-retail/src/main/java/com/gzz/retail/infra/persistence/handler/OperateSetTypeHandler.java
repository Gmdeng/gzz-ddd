package com.gzz.retail.infra.persistence.handler;

import com.gzz.retail.infra.defines.types.OperateType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据类型转换处理
 */
@MappedJdbcTypes(JdbcType.INTEGER)  //数据库类型
@MappedTypes({OperateType.class})   //java数据类型
public class OperateSetTypeHandler implements TypeHandler<Set<OperateType>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Set<OperateType> parameter, JdbcType jdbcType) throws SQLException {
        int value = 0;
        for (OperateType operateType : parameter) {
            value += operateType.value();
        }
        ps.setInt(i , value);
    }

    @Override
    public Set<OperateType> getResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        Set<OperateType> sets = new HashSet<>();
        for (OperateType operateType : OperateType.values()) {
            if((value & operateType.value()) == operateType.value()){
                sets.add(operateType);
            }
        }
        return sets;
    }

    @Override
    public Set<OperateType> getResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        Set<OperateType> sets = new HashSet<>();
        for (OperateType operateType : OperateType.values()) {
            if((value & operateType.value()) == operateType.value()){
                sets.add(operateType);
            }
        }
        return sets;
    }

    @Override
    public Set<OperateType> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        Set<OperateType> sets = new HashSet<>();
        for (OperateType operateType : OperateType.values()) {
            if((value & operateType.value()) == operateType.value()){
                sets.add(operateType);
            }
        }
        return sets;
    }
}
