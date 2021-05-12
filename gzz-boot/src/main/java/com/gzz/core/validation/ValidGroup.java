package com.gzz.core.validation;

/**
 * 分组验证器
 * 通过@GroupSequence注解对组进行排序
 * @GroupSequence({First.class,Second.class})
 */
public class ValidGroup {
    /**
     * 新增组
     */
    public interface ADD { }

    /**
     * 修改组
     */
    public interface MODIFY{ }

    /**
     * 第一组
     */
    public interface FIRST { }

    /**
     * 第二组
     */
    public interface SECOND { }
}
