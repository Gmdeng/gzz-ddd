package com.gzz.retail.infra.persistence;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class MybatisTest {
    private SqlSession sqlSession;
    private IZModuleMapper moduleMapper;

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        // mybatis配置文件，这个地方的root地址为：resources，路径要对。
        String resource = "mybatis-config.xml";
        // 得到配置文件流
        //1.读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 创建会话工厂，传入mybatis的配置文件信息
        //2.创建sqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3.使用工厂生产一个sqlSession对象
//        SqlSession  sqlSession = sqlSessionFactory.openSession();
        // 自动提交事务
//        sqlSession =sqlSessionFactory.openSession(true);
        //4.使用创建Dao接口的代理对象
        //userDao = sqlSession.getMapper(UserDao.class);

        return sqlSessionFactory;
    }

//    @BeforeClass
    @Before
    public void init() throws IOException{
        // mybatis配置文件，这个地方的root地址为：resources，路径要对。
        String resource = "mybatis-config.xml";
        // 得到配置文件流
        //1.读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 创建会话工厂，传入mybatis的配置文件信息
        //2.创建sqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.sqlSession = sqlSessionFactory.openSession();
        this.moduleMapper = sqlSession.getMapper(IZModuleMapper.class);
    }

    @After
    public void tearDown(){
        sqlSession.close();
        log.info("Terminal finish");
    }


    @Test
    public void testQuery() throws IOException {

        List<ZModulePo> users = moduleMapper.findLists(new ParamMap());
        for (ZModulePo user : users) {
            System.out.println(user);
        }
        System.out.println("=*2**************************************===========");
        List<ZModulePo> users1 = moduleMapper.findLists(new ParamMap());
        for (ZModulePo user : users1) {
            System.out.println(user);
        }
        System.out.println("=*3**************************************===========");
        List<ZModulePo> users2 = moduleMapper.findLists(new ParamMap());
        for (ZModulePo user : users2) {
            System.out.println(user);
        }
        System.out.println("=*4**************************************===========");
        List<ZModulePo> users3 = moduleMapper.findLists(new ParamMap());
        for (ZModulePo user : users3) {
            System.out.println(user);
        }
        System.out.println("=*5**************************************===========");
        List<ZModulePo> users4 = moduleMapper.findLists(new ParamMap());
        for (ZModulePo user : users4) {
            System.out.println(user);
        }
    }




    @Test
    public void insert() throws IOException {
        ZModulePo insert = new ZModulePo();
        insert.setId(99L);
        insert.setName("吴俊99");
        moduleMapper.insert(insert);
        sqlSession.commit();
        System.out.println("新增之后=======");
        List<ZModulePo> users = moduleMapper.findList(null);
        for (ZModulePo user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void update() {
        ZModulePo update = new ZModulePo();
        update.setId(99L);
        update.setName("吴俊00");
        moduleMapper.update(update);
        sqlSession.commit();
        System.out.println("更新之后=======");
        List<ZModulePo> users2 = moduleMapper.findList(null);
        for (ZModulePo user : users2) {
            System.out.println(user);
        }
    }

    @Test
    public void delete() {
        moduleMapper.getById(99L);
        sqlSession.commit();
        System.out.println("删除之后========");
        List<ZModulePo> users3 = moduleMapper.findList(null);
        for (ZModulePo user : users3) {
            System.out.println(user);
        }
    }
}