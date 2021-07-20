package com.gzz.retail.domain.account;

import com.gzz.retail.domain.account.entity.AccountDo;
import com.gzz.retail.domain.account.event.TransactionDetailEvent;
import com.gzz.retail.domain.account.service.ITransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 账户的 领域服务
 */
@Service
public class AccountService {
    @Autowired
    private AccountFactory accountFactory;

    /**
     * 转账
     *  @DESC 转账不属于一个对象的动作，但又是这个领域中的一个重要的行为
     *  @param srcAccountNo 源账户
     *  @param destAccountNo 目标账户
     * @param amount 金额
     */
    public void transfer(String srcAccountNo, String destAccountNo, BigDecimal amount){
        AccountDo srcAccount = accountFactory.buildAccount(srcAccountNo);
        AccountDo destAccount = accountFactory.buildAccount(destAccountNo);
        srcAccount.debit(amount);  // 取出
        destAccount.credit(amount); //存入
        // 发布事件
        TransactionDetailEvent event =  new TransactionDetailEvent();

    }

    /**
     * 申请转账
     * @param transaction
     */
    public void apply(ITransaction transaction){

    }
}
