package com.demo.dao;

import com.demo.ds.BaseConfigDTO;
import com.demo.ds.BaseMapperTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.demo.ds.BaseConfigDao;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author lyx
 * 直接继承BaseMapperTest，并指定待持久层测试的接口即可
 */
@RunWith(SpringRunner.class)
public class BaseConfigDaoTest extends BaseMapperTest<BaseConfigDao> {

    public BaseConfigDaoTest() {
        super("mapper/BaseCodeConfigMapper.xml");
    }

    @Test
    public void selectListByCodeTest() {
        String code = "lyx";
        List<BaseConfigDTO> baseConfigList = super.getMapper().selectListByCode(code);
        Assert.assertTrue(baseConfigList.size() > 0);
    }

    @Test
    public void TestPredicate(){
        //Predicate.isEqual()
        List<String> names = Arrays.asList("Adam", "Alexander", "John", "Tom");
        List<String> result = names.stream()
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());

        assertEquals(2, result.size());
        // assertThat(result, contains("Adam","Alexander"));
    }
}