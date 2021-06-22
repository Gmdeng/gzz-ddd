package com.gzz.retail.infra;

import com.gzz.retail.infra.defines.DataStatus;
import junit.framework.JUnit4TestCaseFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

// @RunWith(Suite.class) //声明套件运行器
// @SuiteClasses({TaskTest1.class,TaskTest2.class,TaskTest3.class}) //将需要一起测试的类放进来
public class TestEnumStatus {
    /*
     * 测试套件就是组织测试类一起运行的
     * 写一个作为测试套件的入口类，这个类里不需要包含其他的方法
     * 1.更改测试运行器Suite.class
     * 2.将要测试的类作为数组传入到Suite.SuiteClasses（{}）
     *
     * @RunWith最主要是声明测试的运行器，都在org.junit.runners.下面有声明，其他的一般用不到了
     * @RunWith：可以更改测试运行器 org.junit.runner.Runner；当需要多个或自定义的运行器时用，下一篇具体讲解。

注解大致是就这么多，此外还有一些常用的断言函数：

AssertEquals：断言两个结果相等；
AssertArrayEquals：断言两个数组相等；
AssertNotEquals：断言两个结果不相等；
AssertSame：判断两个对象是否为同一个，不同于equals这里是使用“==”判断；
AssertTrue：断言结果为真；
AssertFalse：断言结果为假；
AssertNull：断言结果为空；
AssertNotNull：断言结果不为空；
AssertThat：使用Matcher做自定义的校验；
————————————————
@Before：会在每一个测试方法被运行前执行一次；一般用于初始化测试数据。注意：有多少个@Test修饰的方法就会执行多少次。
@Before表示每次测试都会先执行一次，一般用于初始化。

@After：会在每一个测试方法运行后被执行一次；一般用于注销测试数据。注意：有多少个@Test修饰的方法就会执行多少次。
@After表示每次测试后都会执行一次，一般用于断开IO连接等。

@BeforeClass：它会在所有的方法运行前执行，static修饰；一般用于测试需要读取文件数据时。注意：不管有多少个@Test修饰的方法只执行一次。

@AfterClass：它会在所有的方法运行结束后执行，static修饰；一般用于断开文件连接。注意：不管有多少个@Test修饰的方法只执行一次。

@Ignore：所修饰的测试方法会被测试运行器忽略；当方法还没写完时可用，写好之后删掉@Ignore即可开始测试。
————————————————
*
     */



    //@Test（expected=XXException.class）：表示这个方法一定会抛出某个异常；如果没有抛出该异常则测试失败。
    //@Test（(timeout=XX）：表示这个方法执行的超时时间，单位毫秒
    @Test
    public void testState(){
        Optional<DataStatus> ds = DataStatus.valueOf(1);
        System.out.println("====================");
        System.out.println(ds.orElseGet(()->{return DataStatus.DISABLE;}));
    }
}
