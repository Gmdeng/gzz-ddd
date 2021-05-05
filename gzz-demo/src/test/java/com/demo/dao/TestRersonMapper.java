package com.demo.dao;

import com.demo.config.DataSourceMasterConfig;
import com.demo.entity.Person;
import com.demo.entity.SexTypeEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


// @RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
//@ContextConfiguration(locations = {"application.yml"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class TestRersonMapper {
    @Autowired
    private IPersonMapper personMapper;
    @Test
    public void TestSelectByPrimaryKey(){
        int ThreadNum = 100;
        CountDownLatch countDownLatch =new CountDownLatch(ThreadNum);
        for (int i = 0; i < ThreadNum ; i++) {
            new Thread(()-> {
                try {
                    countDownLatch.countDown();
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Person p = personMapper.selectByPrimaryKey(5L);
                System.out.println(p.getAddress());
            }
            ).start();
        }
    }

    @Rollback(value = false)
    @Test
    public void testInsert() {
        Person user = new Person();
        user.setName("李昊明677");
        user.setAge(28);
        user.setAddress("北京");
        user.setSex(SexTypeEnum.WOMAN);
        List<String> list = new ArrayList<>();
        list.add("篮球");
        list.add("跑步");
        list.add("游泳");
        user.setHobby(list);
        int resut = personMapper.insertPerson(user);
        Assert.assertEquals(resut, 1);
    }

    @Test
    public void testSelectOne() {
        Person user = personMapper.selectByPrimaryKey(4L);
        Assert.assertNotNull(user);
        System.out.println(user.getSex().getLabel());
        user.getHobby().stream().forEach(name-> System.out.println(name));
    }

    @Rollback(false)
    @Test
    public void testUpdate() {
        Person user = personMapper.selectByPrimaryKey(3L);
        Assert.assertNotNull(user);
        user.setSex(SexTypeEnum.MAN);
        user.getHobby().stream().forEach(name-> System.out.println(name));
        List<String> list = new ArrayList<>();
        list.add("泡吧");
        list.add("看书");
        list.add("看美女");

//        list.add("篮球");
//        list.add("跑步");
//        list.add("游泳");
        user.setHobby(list);
        int resut = personMapper.updatePerson(user);
        Assert.assertEquals(resut, 1);
    }
}
