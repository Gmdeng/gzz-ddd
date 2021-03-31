package com.gzz.retail.infra;

import redis.clients.jedis.*;

import java.util.HashSet;

/**
 *
 * 代码实现 publish 发布消息、subscribe/psubscribe 订阅消息
 */
public class JedisSubscribe {
    /**
     * 机器群
     */
    public static JedisCluster cluster = new JedisCluster(
            new HashSet<HostAndPort>(){{
                add(new HostAndPort("192.168.1.4", 7100));
                add(new HostAndPort("192.168.1.5", 7101));
                add(new HostAndPort("192.168.1.6", 7200));
                add(new HostAndPort("192.168.1.7", 7201));
                add(new HostAndPort("192.168.1.8", 7300));
                add(new HostAndPort("192.168.1.9", 7301));
            }});
    /**
     * 单机
     */
    public static JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "ip", 7001);

    public static String topicLog = "log.*";
    public static String topicLogInfo = "log.info";
    public static String topicLogDebug = "log.info.debug";
    public static String topicLogError = "log.info.debug.error";

    /**
     * 精确频道模式-订阅消息
     * @param topic
     */
    public static void sub(String topic){
        new Thread(()->{
            cluster.subscribe(new JedisPubSub() {
                /**
                 * 完全匹配-接受消息
                 * @param channel 管道
                 * @param message 消息
                 */
                @Override
                public void onMessage(String channel, String message) {
                    System.out.println("onMessage:" + message);
                }
            }, topic);
        }).start();
    }
    /**
     * 匹配频道模式-订阅消息
     * @param topic
     */
    public static void pSub(String topic){
        new Thread(()->{
            // 单机版使用 jedisPool.getResource().psubscribe
            cluster.psubscribe(new JedisPubSub() {
                /**
                 * 通配符匹配-接受消息
                 * @param channel 管道
                 * @param message 消息
                 */
                @Override
                public void onPMessage(String pattern, String channel, String message) {
                    System.out.println("onPMessage:" + message);
                }
            }, topic);
        }).start();
    }
    /**
     * 发布消息
     * @param topic 管道
     * @param message 消息
     */
    public static void publish(String topic,String message){
        // 单机版使用 jedisPool.getResource().psubscribe
        cluster.publish(topic,message);
    }
    public static void main(String[] args) throws InterruptedException {
        sub(topicLog);
        pSub(topicLog);
        int i = 0;
        // 1 秒发送一条消息
        while (true){
            Thread.sleep(1000);
            i ++ ;
            if(i == 1){
                publish(topicLog,topicLog);
            }else if(i == 2){
                publish(topicLogDebug,topicLogDebug);
            }else if(i == 3){
                publish(topicLogInfo,topicLogInfo);
            }else if(i == 4){
                i = 0;
                publish(topicLogError,topicLogError);
            }
        }
    }
}
