package com.gzz.retail.domain.account.primitive;

import lombok.Data;

@Data
public class AccountNo {
    private String no;
    public AccountNo(String accountNo){
        this.no = accountNo;
    }
}
