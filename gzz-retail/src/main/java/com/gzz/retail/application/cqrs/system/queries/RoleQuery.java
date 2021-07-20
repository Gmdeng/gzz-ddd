package com.gzz.retail.application.cqrs.system.queries;

import com.gzz.core.model.BaseQuery;
import com.gzz.core.toolkit.Pager;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模块查询
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleQuery extends BaseQuery {
    public RoleQuery(Pager pager){
        this.setPager( pager);
    }
}
