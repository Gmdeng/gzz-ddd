package com.gzz.retail.facade.gateway;

import com.alibaba.fastjson.JSON;
import com.gzz.core.response.HttpResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
//@SpringBootTest //启动Spring
//配置本地随机端口，服务器会选择一个空闲的端口使用，避免端口冲突
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //配置mock
@ActiveProfiles(profiles = "test")
public class TestAuthRestApi {
    @LocalServerPort
    private int port;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
        session = new MockHttpSession();
        //User user =new User("root","root");
        session.setAttribute("user","user"); //拦截器那边会判断用户是否登录，所以这里注入一个用户
    }
    @Test
    public void testGetUser() throws Exception {
//        HttpResult httpResult = restTemplate.getForObject("http://localhost:9999/auth/getUser?type=ALI", HttpResult.class);
//        Assert.assertNotNull(httpResult);
        mvc.perform(
                MockMvcRequestBuilders.get("http://localhost:9999/v1/auth/getUser?type=ALI")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .session(session))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
                //.andExpect(content().string(equalTo("Hello World!")));
    }
    @Test
    public void testRegisterUser() throws Exception {
       
        mvc.perform(
                MockMvcRequestBuilders.post("http://localhost:9999/v1/auth/getUser?type=ALI")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("")   //POST 数据
                        .session(session))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        //.andExpect(content().string(equalTo("Hello World!")));
    }
    @Test
    public void testGetUserRestful() throws Exception {
        int ThreadNum = 200;
        //
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(ThreadNum);
        for (int i = 0; i < ThreadNum ; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
               try {
                    System.out.println("准。。。。。。。。。。。。" );
                    countDownLatch.await();
                    HttpResult httpResult = restTemplate.getForObject("http://localhost:9999/v1/auth/getUser?type=ALI", HttpResult.class);
                    System.out.println(JSON.toJSONString(httpResult));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
            };
            service.execute(runnable);
            countDownLatch.countDown();
        }
        System.out.println("完成。。。。。。。。。。。。");
       // Assert.assertNotNull(httpResult);

    }
    @Test
    public void testGetUserRestful2() throws Exception {
        int ThreadNum = 2000;

        CountDownLatch countDownLatch =new CountDownLatch(ThreadNum);
        for (int i = 0; i < ThreadNum ; i++) {
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName() +"准。。。。。。。。。。。。" );
                    countDownLatch.countDown();
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HttpResult httpResult =  null;
                try {
                    httpResult = restTemplate.getForObject("http://localhost:9999/v1/auth/getUser?type=ALI", HttpResult.class);
                }catch (Exception ex){
                    System.out.println("Error .....");
                }
                System.out.println(Thread.currentThread().getName() +"going........。。。。。。。。。。。。" );
                System.out.println(JSON.toJSONString(httpResult));
            }
            ).start();
        }
        System.out.println("完成。。。。。。。。。。。。");
        // Assert.assertNotNull(httpResult);

    }
    @Test
    public void testTime() {
        Instant now = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
        System.out.println("秒  数:" + now.getEpochSecond());
        System.out.println("毫秒数:" + now.toEpochMilli());

    }
}
