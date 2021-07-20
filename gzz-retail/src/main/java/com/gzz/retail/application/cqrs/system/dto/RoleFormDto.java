package com.gzz.retail.application.cqrs.system.dto;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class RoleFormDto {
    /**
     * ID id
     */
    private Long id;
    /**
     * 名称 name
     */
    private String name;
    /**
     * 编码（唯一的，java类名） code
     */
    private String code;
    /**
     * 排序 idx
     */
    private int idx;

    /**
     * 排序 idx
     */
    private String notes;

    /**
     * 许可
     */
    private Map<Long, Set<Integer>> permissions;

}
