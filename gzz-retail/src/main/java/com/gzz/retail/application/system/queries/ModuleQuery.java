package com.gzz.retail.application.system.queries;

import com.gzz.core.model.BaseQuery;
import lombok.Data;

/**
 * 模块查询
 */
@Data
public class ModuleQuery extends BaseQuery {
    private String moduleName;
    private String parentId;
}
