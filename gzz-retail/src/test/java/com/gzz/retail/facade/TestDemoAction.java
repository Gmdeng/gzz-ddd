package com.gzz.retail.facade;


import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;


//@ComponentScan(basePackageClasses = {HibernateSessionConfig.class})
//@ActiveProfiles("dev")
//@AutoConfigureMockMvc

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// 这个注解的意义是指定了默认数据源
// @SpringBootTest(classes = MsManageApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestDemoAction {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mvc;

    // user表的name字段，这里为了保证测试时新增和删除的记录是同一条，用UUID作为用户名
    static String testName;

    @BeforeAll
    static void init() {
        testName = UUID.randomUUID().toString().replaceAll("-","");
    }

    @Test
    @Order(1)
    public void testGetTicket() {
        int ThreadNum = 100;
        CountDownLatch countDownLatch = new CountDownLatch(ThreadNum);
        //
        for (int i = 0; i < ThreadNum; i++) {
            new Thread(() -> {
                try {
                    // System.out.println(Thread.currentThread().getName() +"。。。。。。。。。。。。" );
                    countDownLatch.countDown();
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Person person  = restTemplate.getForObject("http://localhost:8080/getTicket?id=5", Person.class);
                // System.out.println(Thread.currentThread().getName() +"going........。。。。。。。。。。。。" );
//                    ResponseEntity<Person> person = restTemplate.getForEntity("http://localhost:9999/demo/getTicket?id=5", Person.class);
//                    System.out.println(person.getStatusCodeValue());
            }
            ).start();
        }
        System.out.println("完成。。。。。。。。。。。。");
    }

    @Test
    public void testGetTicketID() {
//		ResponseEntity<Person> person  = restTemplate.getForEntity("http://localhost:9999/demo/getTicket?id=5", Person.class);
//       //  System.out.println(Thread.currentThread().getName() +"going........。。。。。。。。。。。。" );
//        System.out.println(person.getStatusCodeValue());
    }
}

