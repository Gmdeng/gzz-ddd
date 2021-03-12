package com.gzz.boot;

import com.gzz.boot.aop.exception.ExceptionAspect;
import com.gzz.boot.aop.log.VisitLogAspect;
import com.gzz.boot.aop.resubmit.ResubmitLimitAspect;
import com.gzz.boot.aop.visitrate.VisitRateLimitAspect;
import com.gzz.boot.event.EventPublisher;
import com.gzz.boot.payment.PaymentConfiguration;
import com.gzz.boot.sms.SmsAutoConfiguration;
import com.gzz.boot.sms.SmsProcessor;
import com.gzz.boot.sms.SmsTemplate;
import com.gzz.core.util.IdGenerator;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 约定大于配置
 * Springboot 提供的特性自动关配置---条件注解决问题
 */
@Import({SmsAutoConfiguration.class, PaymentConfiguration.class})
@Configuration
public class AutoConfig implements DisposableBean {
    /**
     * 短信
     * @param smsProcessor
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsTemplate.class)
    public SmsTemplate smsTemplate(SmsProcessor smsProcessor){
        return  new SmsTemplate(smsProcessor);
    }

    /**
     * 访问日志AOP拦截
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="gzz.visitlog.enable", havingValue = "true")
    @ConditionalOnMissingBean(VisitLogAspect.class) //是否已实例添加到容器中
    public VisitLogAspect visitLogAspect() {
        return new VisitLogAspect();
    }
    /**
     * 重复提交AOP拦截
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="gzz.resubmit.enable", havingValue = "true")
    @ConditionalOnMissingBean(ResubmitLimitAspect.class) //是否已实例添加到容器中
    public ResubmitLimitAspect resubmitLimitAspect() {
        return new ResubmitLimitAspect();
    }

    /**
     * 访问频率每秒限流AOP拦截
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="gzz.visitrate.enable", havingValue = "true")
    @ConditionalOnMissingBean(VisitRateLimitAspect.class) //是否已实例添加到容器中
    public VisitRateLimitAspect visitRateLimitAspect() {
        return new VisitRateLimitAspect();
    }

    /**
     * 异常处理 AOP拦截
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ExceptionAspect.class)
    public ExceptionAspect exceptionAspect() {
        return new ExceptionAspect();
    }

    /**
     * 事件发布器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(EventPublisher.class)
    public EventPublisher eventPublisher() {
        return new EventPublisher();
    }

    /**
     * ID生成器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(IdGenerator.class)
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }

    @Override
    public void destroy() throws Exception {
        // log.info("开始销毁Es的连接");
//        if (transportClient != null) {
//            transportClient.close();
//        }
    }
}