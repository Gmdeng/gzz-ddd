package com.gzz.retail.application.cqrs.goods.queries;

import com.gzz.core.model.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GoodsSpuQuery extends BaseQuery {
    private String moduleName;
    private String parentId;
    private String code;
}
