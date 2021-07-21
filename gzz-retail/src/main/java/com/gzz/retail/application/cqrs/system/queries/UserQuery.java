package com.gzz.retail.application.cqrs.system.queries;

import com.gzz.core.model.BaseQuery;
import com.gzz.core.toolkit.Pager;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询参数
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserQuery extends BaseQuery {
    public UserQuery(Pager pager){
        this.setPager(pager);
    }
}
