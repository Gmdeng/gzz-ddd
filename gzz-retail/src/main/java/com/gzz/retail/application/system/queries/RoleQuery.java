package com.gzz.retail.application.system.queries;

import com.gzz.core.model.BaseQuery;
import com.gzz.core.toolkit.Pager;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模块查询
 */
@Data
@NoArgsConstructor
public class RoleQuery extends BaseQuery {
    public RoleQuery(Pager pager){
        this.setPager( pager);
    }
}
