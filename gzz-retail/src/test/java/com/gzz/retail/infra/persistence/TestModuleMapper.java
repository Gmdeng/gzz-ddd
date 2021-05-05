package com.gzz.retail.infra.persistence;

import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest //缓存mybatsitest注解
//这个是启用自己配置的数据元，不加则采用虚拟数据源
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)    //这个是默认是回滚，不会commit入数据库，改成false 则commit
public class TestModuleMapper {
    @Autowired
    private IZModuleMapper moduleMapper;
    @Test
    public void testModuleSelect(){
        log.error("|||ERROR {}, {}", 33, "$FFD");
        List<ZModulePo> data = moduleMapper.findLists(new ParamMap());
        for (ZModulePo po : data) {
            System.out.println(po.getName() +"=========="+ po.getId());
        }
        System.out.println("==========2=========================");
        List<ZModulePo> data1 = moduleMapper.findLists(new ParamMap());
        for (ZModulePo po : data1) {
            System.out.println(po.getName() +"=========="+ po.getId());
        }
        System.out.println("==========3=========================");
        List<ZModulePo> data2 = moduleMapper.findLists(new ParamMap());
        for (ZModulePo po : data2) {
            System.out.println(po.getName() +"=========="+ po.getId());
        }
    }

    @Test
    public void testUpdate(){
        ZModulePo po = new ZModulePo();
        po.setCode("SUPER-ROOT");
        po.setIcon("https://wwww.g-zz.com");
        po.setId(22L);
        po.setIdx(2);
        po.setName("783500d33c9e4d6bb6e959336e84fa7f");
        po.setParentId(0L);
        po.setType("M");
        po.setUrl("wss://g-zz.com");
        int num = moduleMapper.update(po);
        Assert.assertEquals(num, 1);
    }
}
