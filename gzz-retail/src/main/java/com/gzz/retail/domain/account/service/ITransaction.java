package com.gzz.retail.domain.account.service;

/**
 * 转账
 */
public interface ITransaction {
//    private Account fromAccount;  // 源账户
//    private Account destAccount; // 目标账户
//    private BigDecimal amount;  // 金额

    /**
     * 执行
     */
    public void execute() throws Exception;
}
