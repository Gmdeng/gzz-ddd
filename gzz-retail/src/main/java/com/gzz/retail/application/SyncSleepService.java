package com.gzz.retail.application;

import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SyncSleepService {
    public void syncSleep() throws InterruptedException {
        System.out.println("线程名: " +Thread.currentThread().getName());
        System.out.println("开始同步休眠3秒");
        Thread.sleep(3000);
        System.out.println("同步休眠结束");
    }

    // @Async
    public void asyncSleep() throws InterruptedException {
        System.out.println("次线程 "+Thread.currentThread().getName());

        System.out.println("开始异步休眠3秒");
        Thread.sleep(3000);
        System.out.println("异步休眠休眠结束");
    }
}
