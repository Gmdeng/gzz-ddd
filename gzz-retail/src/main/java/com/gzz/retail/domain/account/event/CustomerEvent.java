package com.gzz.retail.domain.account.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEvent {
    private String name;
    private Boolean isCustomer;
}
