package com.gzz.retail.infra.persistence;

import com.gzz.core.util.StringUtil;
import com.gzz.retail.infra.persistence.mapper.IZRoleMapper;
import com.gzz.retail.infra.persistence.pojo.ZRolePermissionPo;
import com.gzz.retail.infra.persistence.pojo.ZRolePo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest //缓存mybatsitest注解
//这个是启用自己配置的数据元，不加则采用虚拟数据源
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)    //这个是默认是回滚，不会commit入数据库，改成false 则commit
public class TestRoleMapper {
    @Autowired
    private IZRoleMapper roleMapper;

    @Test
    @Rollback(false)  // false 则commit
    @Transactional
    public void testInsert(){
        ZRolePo rolePo = new ZRolePo();
        rolePo.setName(StringUtil.randomNum(8));
        rolePo.setIdx(3);
        rolePo.setNotes(StringUtil.randomChar(20));
        rolePo.setCode("CODE");
        rolePo.setUpdateBy("Tester");
        rolePo.setUpdateOn(new Date());
        rolePo.setCreateBy("Tester");
        rolePo.setCreateOn(new Date());
        //
        int insertNum = roleMapper.insert(rolePo);
        System.out.println("insertNum: " + insertNum);
        List<ZRolePermissionPo> permissionPos = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            ZRolePermissionPo permissionPo = new ZRolePermissionPo();
            permissionPo.setRoleId(rolePo.getId());
            permissionPo.setModuleId(i * 1L);
            permissionPo.setHasPower(Integer.parseInt(StringUtil.randomNum(2)));
            permissionPos.add(permissionPo);
        }

        int clearNum =  roleMapper.clearPermissions(rolePo.getId());
        System.out.println("clearNum: " + clearNum);
        int batchNum = roleMapper.batchInsertPermission(permissionPos);
        System.out.println("batchNum: " + batchNum);
    }


}
