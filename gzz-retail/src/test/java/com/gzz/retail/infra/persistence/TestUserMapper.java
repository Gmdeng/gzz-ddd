package com.gzz.retail.infra.persistence;

import com.gzz.retail.infra.defines.CommStatus;
import com.gzz.retail.infra.defines.DataStatus;
import com.gzz.retail.infra.persistence.mapper.IZUserMapper;
import com.gzz.retail.infra.persistence.pojo.ZUserPo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@Slf4j
//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest //缓存mybatsitest注解
//这个是启用自己配置的数据元，不加则采用虚拟数据源
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)    //这个是默认是回滚，不会commit入数据库，改成false 则commit
public class TestUserMapper {
    @Autowired
    private IZUserMapper userMapper;

    @Test
    public void testInsert(){
        ZUserPo po = new ZUserPo();
        po.setUserId("userId");
        po.setPasswd("password");
        po.setSalt("salt");
        po.setPetName("petName");
        po.setCreateOn(new Date());
        po.setUpdateOn(new Date());
        po.setCreateBy("testing");
        po.setUpdateBy("testing");

        po.setStatus(CommStatus.UNAUDIT);
        userMapper.insert(po);
    }

    @Test
    public void testSelectOne(){
        ZUserPo po = userMapper.getById(4L);
        System.out.println(po.getStatus().getKey());
        System.out.println(po.getStatus().getLabel());
    }
}
