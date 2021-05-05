package com.gzz.core.model;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import lombok.Data;
import org.springframework.cglib.beans.BeanMap;

@Data
public abstract class BaseQuery {
    private Pager pager;

    public ParamMap toParam(){
        BeanMap beanMap =  BeanMap.create(this);
        ParamMap paramMap = new ParamMap();
        for (Object key : beanMap.keySet()) {
            Object value = beanMap.get(key);
            paramMap.put(key+"", value);
        }

        return paramMap;
    }
}
