package com.gzz.retail.domain.account.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户交易明细
 * 值对象
 */
@Data
@Accessors(chain = true)
public class TransactionDetail {
    //对象标识
    private long id;
    // 交易类型
    private TransactionType type;
    // 所属账户
    private Account account;
    // 交易发生金额
    private BigDecimal amount;
    // 交易发生手续费
    private BigDecimal fee;
    // 交易发生时间
    private Date occurrenON;
}
