package com.gzz.retail.domain.account;

import com.gzz.retail.domain.account.entity.AccountDo;
import com.gzz.retail.domain.account.primitive.AccountId;
import com.gzz.retail.domain.account.primitive.AccountNo;
import com.gzz.retail.domain.system.primitive.UserId;

/**
 * 领域仓库
 */
public interface IAccountRepo {
    AccountDo find(AccountId id);
    AccountDo find(AccountNo AccountNo);
    AccountDo find(UserId userId);
    AccountDo save(AccountDo account);
}
