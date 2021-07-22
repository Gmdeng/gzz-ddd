package com.gzz.boot.mybatis.interceptor;

import com.gzz.core.toolkit.Pager;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * MyBatis 物理分页拦截器
 *
 * @author Gm
 */
@Slf4j
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PaginationInterceptor implements Interceptor {
    private String DIALECT = "MYSQL";
    private final static ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private final static ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private final static ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    @Override
    public Object intercept(Invocation inv) throws Throwable {
        //log.debug("进入了注解。MyBatisPaginationInterceptor。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
        // 获取StatementHandler, 默认是RoutingStatementHandler
        StatementHandler statementHandler = (StatementHandler) inv.getTarget();
        // RoutingStatementHandler statementHandler = (RoutingStatementHandler)inv.getTarget();

        // 获取statementHandler包装类
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        String[] properties = metaObject.getGetterNames();
        log.info("SetterNames {}", properties);
//        Map<String, Object> parameterObject = (Map<String, Object>) metaObject.getValue("parameterHandler.parameterObject");
//        log.info("parameterObject {}", parameterObject);
        // 获取查询接口映射的相关信息
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // 获取方法名称
        String msId = mappedStatement.getId();
        Configuration configuration = mappedStatement.getConfiguration();
        // 拦截以.ByPager结尾的请求。分页功能的统一实现
        if (msId.matches(".*ByPage$")) {
            // 获取原SQL语句
            BoundSql boundSql = statementHandler.getBoundSql();
            String sqlQuery = boundSql.getSql();
            /* ============================================================= */
            // 获取进行数据库操作管理参数的handler , 获取请求时的参数MAP
            // ParameterHandler parameterHandler = (ParameterHandler) metaStatementHandler.getValue("delegate.parameterHandler");
            // Object param = parameterHandler.getParameterObject();
            // 获取方法的参数
            Object params = boundSql.getParameterObject();
            // log.info("params {}", params);
            // 获取参数值META
            MetaObject metaParam = MetaObject.forObject(params, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,
                    DEFAULT_REFLECTOR_FACTORY);
            // 获取分页参数
            Pager pager = GetPager(params, metaParam);
            // 获取当前连接对象
            Connection connection = (Connection) inv.getArgs()[0];
            // 统计总记录
            int totalRecord = calcTotalRecords(sqlQuery, connection, boundSql.getParameterMappings(), metaParam);
            pager.setTotalRecord(totalRecord);

            // 重新构建为分页功能的SQL语句
            sqlQuery = RebuildSQL(sqlQuery, pager.getOffset(), pager.getPageSize());
            log.info("重构分页后的SQL语句: " + sqlQuery);
            // 将构建完成的SQL语句赋值个体""
            metaObject.setValue("delegate.boundSql.sql", sqlQuery);
            // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
            metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
            metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        }
        // 将执行权交给下一个拦截器
        return inv.proceed();
    }

    // 获取代理对象
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties prop) {
        this.DIALECT = prop.getProperty("dialect", "mysql");
    }

    /**
     * 构建分页SQL
     *
     * @param sql
     * @param offset   每页数
     * @param pageSize
     * @return
     */
    private String RebuildSQL(String sql, int offset, int pageSize) {
        if (this.DIALECT.equalsIgnoreCase("mysql")) {
            sql = sql + " limit " + offset + "," + pageSize;
        } else if (this.DIALECT.equalsIgnoreCase("oracle")) {
            sql = " SELECT t.* FROM ( SELECT A.*, ROWNUM as rowno FROM (" + sql + ") A WHERE ROWNUM <= "
                    + (offset + pageSize) + ") t WHERE t.rowno > " + offset;
        }
        return sql;
    }

    /**
     * 获取分页类
     * @param params
     * @return
     */
    private Pager GetPager(Object params, MetaObject metaObj){
        if (params instanceof Pager) {
            return (Pager) params;
        } else if (params instanceof java.util.HashMap) {
            return  (Pager) metaObj.getValue("pager");
        } else {
            return new Pager();
        }
    }
    /**
     * 统计总记录SQL
     *
     * @param sql
     * @param conn
     * @param mappings
     * @param metaObject
     * @return
     */
    private int calcTotalRecords(String sql, Connection conn, List<ParameterMapping> mappings, MetaObject metaObject) {
        // 统计总记数SQL语句
        int index = sql.toLowerCase().indexOf("from");
        // String countSql = "select count(0) as tot from (" + sql + ") t";
        String countSql = "select count(1) as tot " + sql.substring(index);
        int totalRecord = 0; // 总记录数
        ResultSet rs = null;
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(countSql);
            for (int i = 0; i < mappings.size(); i++) {
                ParameterMapping paramMapping = mappings.get(i);
                String paramName = paramMapping.getProperty();
                String paramValue = (String) metaObject.getValue(paramName);
                prepStmt.setString(i + 1, paramValue);
            }
            rs = prepStmt.executeQuery();
            if (rs.next())
                totalRecord = rs.getInt(1); // rs.getInt("tot");
        } catch (SQLException e) {
            log.error("分页查询统计记录出错：[" + countSql + "]" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (prepStmt != null)
                    prepStmt.close();
            } catch (SQLException e) {
                log.error("分页查询统计记录关闭资源出错：[" + countSql + "]" + e.getMessage());
                e.printStackTrace();
            }
        }
        return totalRecord;
    }
}