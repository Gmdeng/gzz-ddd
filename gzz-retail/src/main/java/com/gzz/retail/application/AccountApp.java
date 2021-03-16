package com.gzz.retail.application;

import com.gzz.retail.domain.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @Lazy 延迟依赖注入
 * @Profile 指定各个环境的配置，然后通过某个配置来开启某一个环境，方便切换
 */
@Lazy
@Profile("remote")
@Service
public class AccountApp {
    @Autowired
    private Account account;

}
