package com.gzz.retail.domain.account;

import com.gzz.retail.domain.account.entity.AccountDo;
import org.springframework.stereotype.Service;

/**
 * 领域工厂
 */
@Service
public class AccountFactory {
    /**
     * 创建，账号
     * @return
     */
    public AccountDo buildAccount(){
        return new AccountDo();
    }
    public AccountDo buildAccount(String accountNo){
        return new AccountDo();
    }
}
