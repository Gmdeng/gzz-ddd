package com.gzz.boot.mybatis.autofill;

import com.gzz.core.request.Operator;
import com.gzz.core.request.OperatorContextHolder;
import com.gzz.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 新增修改时自动填写，时间，操作人
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class AutoFillInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 新增修改时
        if(SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
            Object parameter = invocation.getArgs()[1];
            Field[] fields = parameter.getClass().getDeclaredFields();
            for (Field f : fields) {
                AutoFillTime fillTime = f.getAnnotation(AutoFillTime.class);
                AutoFillUser fillUser = f.getAnnotation(AutoFillUser.class);
                Date d = null;
                if (fillTime != null) {
                    if (StringUtil.isEmpty(fillTime.value())) {
                        d = new Date();
                    } else {
                        try{
                            SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
                            d = sdf.parse(fillTime.value());
                        }catch (Exception e){
                            log.warn("无效的日期默认值");
                            d= new Date();
                        }
                    }
                    SetFieldValue(f, new Date(), parameter);
                }
                if (fillUser != null) {
                    String userCode = StringUtil.isEmpty(fillUser.value()) ? currentUser() : fillUser.value();
                    SetFieldValue(f, userCode, parameter);
                }
            }
        }
        //
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // 生成object对象的动态代理对象
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String maxTime = properties.getProperty("maxTime");
        System.out.println(properties);
        System.out.println(maxTime);
    }
    // 获取当前用户id
    private String currentUser(){
        Operator user = OperatorContextHolder.GetOperator();
        if(user == null) {
            return "AutoModifyer";
        }else{
            return  user.getUserId();
        }
    }

    /**
     * 属性设值
     * @param f
     * @param value
     * @param clzz
     * @throws IllegalAccessException
     */
    private void SetFieldValue(Field f, Object value, Object clzz) throws IllegalAccessException {
        // 获取原来的访问控制权限
        boolean accessFlag = f.isAccessible();
        if(!f.isAccessible())
            f.setAccessible(true);
        f.set(clzz, value);
        f.setAccessible(accessFlag);
    }
}
