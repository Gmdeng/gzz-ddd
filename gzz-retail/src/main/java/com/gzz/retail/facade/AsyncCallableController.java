package com.gzz.retail.facade;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.concurrent.Callable;

@Controller
public class AsyncCallableController {
    @RequestMapping("/demo/async/test")
    @ResponseBody
    public Callable<String> callable() {
        // 这么做的好处避免web server的连接池被长期占用而引起性能问题，
        // 调用后生成一个非web的服务线程来处理，增加web服务器的吞吐量。
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3 * 1000L);
                return "小单 - " + System.currentTimeMillis();
            }
        };
    }


    /**
     * 异步方法
     * @return
     */
    @RequestMapping("/demo/asyn")
    public String getAsynHello(){
        long n = Instant.now().toEpochMilli();
        //异步
        //String s = helloService.asynchSayHello();

        long f = Instant.now().toEpochMilli();
        return "s " + " 时间: " + (f-n);

    }

    /**
     * 同步方法
     * * @return
     */
    @RequestMapping("/demo/sync")
    public String getSyncHello(){

        long n = Instant.now().toEpochMilli();
        //同步
        //String s = helloService.synchSayHello();

        long f = Instant.now().toEpochMilli();
        return "s " + " 时间: " + (f-n);
    }
}
