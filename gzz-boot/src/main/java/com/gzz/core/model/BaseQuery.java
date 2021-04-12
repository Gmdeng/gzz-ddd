package com.gzz.core.model;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import lombok.Data;


@Data
public class BaseQuery {
    private Pager pager;

    public ParamMap toParam(){
        return new ParamMap();
    }
}