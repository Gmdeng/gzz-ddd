package com.gzz.retail.infra.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 使用异步时启用线程池
 *
 * @Async
 */
@EnableAsync //开启异步调用
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    /**
     *  springboot异步操作可以使用@EnableAsync和@Async两个注解，本质就是多线程和动态代理
     *  异步方法使用注解@Async ,返回值为void或者Future
     *  异步方法和调用方法一定要写在不同的类中,如果写在一个类中是没有效果的
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 这一步千万不能忘了，否则报错： java.lang.IllegalStateException: ThreadPoolTaskExecutor not initialized
        // 而且最好放在最上面  否则下面set方法对Executor都不会生效
        executor.initialize();

        executor.setCorePoolSize(10); //核心线程数
        executor.setMaxPoolSize(20);  //最大线程数
        executor.setQueueCapacity(1000); //队列大小
        executor.setKeepAliveSeconds(300); //线程最大空闲时间

        executor.setAwaitTerminationSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);

        executor.setThreadNamePrefix("fsx-Executor-"); ////指定用于新创建的线程名称的前缀。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略（一共四种，此处省略）
        return executor;
    }

    // 异常处理器：当然你也可以自定义的，这里我就这么简单写了~~~
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

}
