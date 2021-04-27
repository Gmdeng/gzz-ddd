package com.gzz.retail.infra.persistence;

import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
// 这个注解的意义是指定了默认数据源
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestModuleMapper {
    @Autowired
    private IZModuleMapper moduleMapper;
    @Test
    public void testModuleSelect(){
        List<ZModulePo> data = moduleMapper.findList(new ParamMap());
        for (ZModulePo po : data) {
            System.out.println(po.getName() +"=========="+ po.getId());
        }
    }
}
