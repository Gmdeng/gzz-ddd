package com.gzz.retail.application.cqrs.system.queries;

import com.gzz.core.model.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模块查询
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ModuleQuery extends BaseQuery {
    private String moduleName;
    private String parentId;
    private String code;
}
