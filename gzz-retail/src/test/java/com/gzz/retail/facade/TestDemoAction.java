package com.gzz.retail.facade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDemoAction {
    @Autowired
    private RestTemplate restTemplate;

    @Test
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

