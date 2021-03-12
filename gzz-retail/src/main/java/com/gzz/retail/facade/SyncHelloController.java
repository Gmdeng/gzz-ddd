package com.gzz.retail.facade;

import com.gzz.retail.application.SyncHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * 同步异步测试
 */
@RestController
@RequestMapping("/demo")
public class SyncHelloController {
    @Autowired
    private SyncHelloService helloService;

    /**
     * 异步方法
     * @return
     */
    @RequestMapping("/asyn1")
    public String getAsynHello(){
        long n = Instant.now().toEpochMilli();
        //异步
        String s = helloService.asynchSayHello();

        long f = Instant.now().toEpochMilli();
        return s + " 时间: " + (f-n);

    }

    /**
     * 同步方法
     * * @return
     */
    @RequestMapping("/sync1")
    public String getSyncHello(){

        long n = Instant.now().toEpochMilli();
        //异步
        String s = helloService.synchSayHello();

        long f = Instant.now().toEpochMilli();
        return s + " 时间: " + (f-n);
    }

    /**
     * 线程异步方法
     * 自己直接开启线程比@Async要快几毫秒,不过可以忽略不计了
     * * @return
     */
    @RequestMapping("/thread/asyn")
    public String getThreadSyncHello(){
        long n = Instant.now().toEpochMilli();
        //异步
        String s = helloService.threadAsynchSayHello();
        long f = Instant.now().toEpochMilli();
        return s + " 时间: " + (f-n);
    }
}
