package com.gzz.core.model;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import lombok.Data;
import org.springframework.cglib.beans.BeanMap;


/**
 * CQRS 查询接收基类
 */
@Data
public abstract class BaseQuery {
    private Pager pager;
    private String keywords;

    /**
     * 转为查询参数
     * @return
     */
    public ParamMap toParam(){
        BeanMap beanMap =  BeanMap.create(this);
        ParamMap paramMap = new ParamMap();
        for (Object key : beanMap.keySet()) {
            if(key.equals("pager")) continue;
            Object value = beanMap.get(key);
            paramMap.put(key+"", value);
        }
        return paramMap;
    }
}

