package com.gzz.boot.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

// import org.apache.ibatis.type.EnumOrdinalTypeHandler;

public class EnumSetTypeHandler<E extends Enum<E>> extends BaseTypeHandler<Set<E>> {
    private final Class<E> type;
    private final E[] enums;

    public EnumSetTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
//        ResolvableType demo= ResolvableType.forClass(type);
//        demo.getInterfaces()[0].getGeneric(1).resolve();
        this.enums = type.getEnumConstants();
//        type.getInterfaces().getClass().getEnumConstants()
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<E> parameter, JdbcType jdbcType) throws SQLException {
        // ps.setInt(i, parameter.ordinal());
        int value = 0;
        for (E p : parameter) {
            value += p.ordinal();
        }
        ps.setInt(i, value);
    }

    @Override
    public Set<E> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int ordinal = rs.getInt(columnName);
        if (ordinal == 0 && rs.wasNull()) {
            return null;
        }
        return toOrdinalEnum(ordinal);
    }

    @Override
    public Set<E> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int ordinal = rs.getInt(columnIndex);
        if (ordinal == 0 && rs.wasNull()) {
            return null;
        }
        return toOrdinalEnum(ordinal);
    }

    @Override
    public Set<E> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int ordinal = cs.getInt(columnIndex);
        if (ordinal == 0 && cs.wasNull()) {
            return null;
        }
        return toOrdinalEnum(ordinal);
    }

    private Set<E> toOrdinalEnum(int ordinal) {
        try {
            // return enums[ordinal];
            Set<E> sets = new HashSet<>();
            for (E p : enums) {
                if ((ordinal & p.ordinal()) == p.ordinal())
                    sets.add(p);
            }
            return sets;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + ordinal + " to " + type.getSimpleName() + " by ordinal value.", ex);
        }
    }
}