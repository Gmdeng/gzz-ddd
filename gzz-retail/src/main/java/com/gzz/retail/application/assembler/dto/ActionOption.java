package com.gzz.retail.application.assembler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作选项
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionOption  {
    private Integer value;
    private String label;
}
