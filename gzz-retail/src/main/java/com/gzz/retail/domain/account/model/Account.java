package com.gzz.retail.domain.account.model;

import com.gzz.boot.event.EventPublisher;
import com.gzz.retail.domain.account.event.TransactionDetailEvent;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
@Accessors(chain = true)
public class Account {
    // 对象标识
    private long id;
    // 账户号
    private String no;
    // 余额
    private BigDecimal balance;
    // 可用金额
    private BigDecimal availAmount;
    // 冻结金额
    private BigDecimal frozenAmount;
    // 是否有效标识
    private int status;
    // 账户信用等级
    private int creditDegree;

    /**
     *
     * 把固定金额转入指定账户
     *
     * @param destAccount 输入账户
     */
    public void transferTo(Account destAccount, BigDecimal amount) {
        // TODO
        TransactionDetail detail = new TransactionDetail();
//        detail.setAccount(this)
//                .setAmount(new BigDecimal(23.22))
//                .setType(TransactionType.TRANSFER_TO)
//                .setFee(new BigDecimal(0.33)).setOccurrenON(new Date());
        EventPublisher.publish(new TransactionDetailEvent()
                .setDetail(detail)
                .setOrigin("")
                .setTarget(""));
    }

    /**
     * 存入
     *
     * @param amount 金额
     */
    public void deposit(BigDecimal amount) {

    }


    /**
     * 向账户存入金额，贷记
     *
     * @param amount 金额
     */
    public void credit(BigDecimal amount) {

    }

    /**
     * 从账户划出金额，借记
     *
     * @param amount 金额
     */
    public void debit(BigDecimal amount) {

    }


    /**
     * 冻结金额
     *
     * @param amount 金额
     */
    public void doFrozen(BigDecimal amount) {

    }

    /**
     * 解冻金额
     *
     * @param amount 金额
     */
    public void doUnFrozen(BigDecimal amount) {

    }

    /**
     * 冻结账户
     */
    public void doLock() {

    }

    /**
     * 解冻账户
     */
    public void doUnLock() {

    }
}
