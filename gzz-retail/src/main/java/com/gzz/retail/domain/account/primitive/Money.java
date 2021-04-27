package com.gzz.retail.domain.account.primitive;

import java.math.BigDecimal;

/**
 * 支付金额 + 支付货币
 */
public class Money {
    private BigDecimal amount;
    private Currency currency;
    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }
}
