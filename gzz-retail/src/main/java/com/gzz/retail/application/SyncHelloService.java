package com.gzz.retail.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SyncHelloService {
    @Autowired
    private SyncSleepService sleepService;

    public String synchSayHello() {
        try {
            sleepService.syncSleep();
            return "hello world,这是同步方法";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String asynchSayHello() {
        try {
            System.out.println("主线程 " + Thread.currentThread().getName());
            sleepService.asyncSleep();
            return "hello world,这是异步方法";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String threadAsynchSayHello() {
        //还是休眠3秒
        Runnable runnable = () -> {
            try {
                sleepService.syncSleep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        System.out.println(Thread.currentThread().getName() + "  开启新线程");
        new Thread(runnable).start();
        return "hello world,这是开启线程的异步方法";
    }
}
