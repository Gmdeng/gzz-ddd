package com.gzz.retail.application.system.queries;

import com.gzz.core.model.BaseQuery;
import lombok.Data;

@Data
public class ModuleQuery extends BaseQuery {
    private String moduleName;
    private String parentId;
}
