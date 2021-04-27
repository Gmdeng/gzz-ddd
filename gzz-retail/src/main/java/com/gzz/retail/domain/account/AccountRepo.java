package com.gzz.retail.domain.account;

import com.gzz.retail.domain.account.entity.AccountDo;
import com.gzz.retail.domain.account.primitive.AccountId;
import com.gzz.retail.domain.account.primitive.AccountNo;
import com.gzz.retail.domain.system.primitive.UserId;

/**
 * 领域仓储
 */
public class AccountRepo implements IAccountRepo{
    @Override
    public AccountDo find(AccountId id) {
        return null;
    }

    @Override
    public AccountDo find(AccountNo AccountNo) {
        return null;
    }

    @Override
    public AccountDo find(UserId userId) {
        return null;
    }

    @Override
    public AccountDo save(AccountDo account) {
        return null;
    }
}
