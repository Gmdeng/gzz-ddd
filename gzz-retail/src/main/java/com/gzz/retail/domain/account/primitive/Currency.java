package com.gzz.retail.domain.account.primitive;

import java.math.BigDecimal;

/**
 * 货币
 */
public class Currency {
    private String code;
    private BigDecimal rate = new BigDecimal(1);
    public Currency(String code){
        this.code = code;
    }
    public Currency(String code, BigDecimal rate){
        this.code = code;
        this.rate = rate;
    }
}
