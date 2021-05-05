package com.gzz.retail.application.system.queries;

import com.gzz.core.model.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends BaseQuery {
    private String name;
    private String address;
}
