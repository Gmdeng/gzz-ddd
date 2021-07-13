package com.gzz.retail.application.cqrs.goods.queries;

import com.gzz.core.model.BaseQuery;
import lombok.Data;

/**
 * 分类查询
 */
@Data
public class CatalogQuery extends BaseQuery {
    private String moduleName;
    private String parentId;
    private String code;
}
