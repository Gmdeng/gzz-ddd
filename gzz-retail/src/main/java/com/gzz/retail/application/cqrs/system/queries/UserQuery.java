package com.gzz.retail.application.cqrs.system.queries;

import com.gzz.core.model.BaseQuery;
import com.gzz.core.toolkit.Pager;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserQuery extends BaseQuery {
    public UserQuery(Pager pager){
        this.setPager(pager);
    }
    private String name;
    private String address;
}
