package com.gzz.retail.domain.account;

import com.gzz.retail.domain.account.entity.Account;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class AccountFactory {
    public Account buildAccount(){
        return new Account();
    }
    public Account buildAccount(String accountNo){
        return new Account();
    }
}
