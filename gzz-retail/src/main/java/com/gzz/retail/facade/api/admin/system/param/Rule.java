package com.gzz.retail.facade.api.admin.system.param;

import lombok.Data;

@Data
public class Rule {
    /**
     * 类名
     */
    private String className;

    /**
     * 字段名称
     */
    private String column;

    /**
     * 操作符
     */
    private String operate;

    /**
     * 对应值
     */
    private String[] value;
}
