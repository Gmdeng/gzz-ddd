package com.gzz.retail.infra.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @MybatisTest 默认会使用虚拟的数据源替代你配置的，如果想使用你配置的数据源操作真正的数据库则使用
 * Replace.NONE表示不替换数据源配置
 */
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestMemberRepo {
    @Test
    @Rollback
    public void insert() {

    }
}
