package com.gzz.retail.infra.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Field;

//Configuration
public class RedisCluterConfig extends CachingConfigurerSupport{

    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;


    @Bean(name="clusterConfig")
    public RedisClusterConfiguration clusterConfig(){
        RedisClusterConfiguration config = new RedisClusterConfiguration();
        String[] nodes = clusterNodes.split(",");
        for(String node : nodes){
            String[] host =  node.split(":");
            RedisNode redis = new RedisNode(host[0], Integer.parseInt(host[1]));
            config.addClusterNode(redis);
        }
        return config;
    }

    @Bean(name="factory")
    public RedisConnectionFactory factory(RedisClusterConfiguration clusterConfig){
        JedisConnectionFactory factory = new JedisConnectionFactory(clusterConfig, createJedisPoolConfig());
        return factory;
//        //单机版jedis
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        //设置redis服务器的host或者ip地址
//        redisStandaloneConfiguration.setHostName("localhost");
//        //设置默认使用的数据库
//        redisStandaloneConfiguration.setDatabase(0);
//        //设置密码
//        redisStandaloneConfiguration.setPassword(RedisPassword.of("123456"));
//        //设置redis的服务的端口号
//        redisStandaloneConfiguration.setPort(16379);
//        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpccb =
//                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        jpccb.poolConfig(createJedisPoolConfig());
//        //通过构造器来构造jedis客户端配置
//        JedisClientConfiguration jedisClientConfiguration = jpccb.build();
//        //单机配置 + 客户端配置 = jedis连接工厂
//        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);

    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // enableDefaultTyping 过期解决
        // 解决方法：使用activateDefaultTyping方法替换掉enableDefaultTyping
        //替换方法为：
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 经过反射获取JedisCluster
     * @param factory
     * @return
     */
    @Bean
    public JedisCluster redisCluster(RedisConnectionFactory factory){
        Object object =null;
        try {
            object= getFieldValueByObject(factory,"cluster");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (JedisCluster)object;

    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    public  Object getFieldValueByObject (Object object , String targetFieldName) throws Exception {
        // 获取该对象的Class
        Class objClass = object.getClass();
        // 获取全部的属性数组
        Field[] fields = objClass.getDeclaredFields();
        for (Field field:fields) {
            // 属性名称
            field.setAccessible(true);
            String currentFieldName = field.getName();
            if(currentFieldName.equals(targetFieldName)){
                return field.get(object); // 经过反射拿到该属性在此对象中的值(也多是个对象)
            }
        }
        return null;
    }


    public JedisPoolConfig createJedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        //最大连接数, 默认8个
        config.setMaxTotal(5000);
        //最小空闲链接数, 默认0
        config.setMinIdle(20);
        //当池内没有可用的连接时，最大等待时间
        //获取链接时的最大等待毫秒数(若是设置为阻塞时BlockWhenExhausted),若是超时就抛异常, 小于零:阻塞不肯定的时间,  默认-1
        config.setMaxWaitMillis(10000);
        // 在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(true);
        //最大空闲链接数, 默认8个
        config.setMaxIdle(2000);

        //------以下属性根据需要自行添加-------------

        //链接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setBlockWhenExhausted(false);
        //设置的逐出策略类名, 默认DefaultEvictionPolicy(当链接超过最大空闲时间,或链接数超过最大空闲链接数)
        config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        //是否启用pool的jmx管理功能, 默认true
        config.setJmxEnabled(true);
        //MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
        config.setJmxNamePrefix("pool");
        //是否启用后进先出, 默认true
        config.setLifo(true);

        //逐出链接的最小空闲时间 默认1800000毫秒(30分钟)
        config.setMinEvictableIdleTimeMillis(1800000);

        //每次逐出检查时 逐出的最大数目 若是为负数就是 : 1/abs(n), 默认3
        config.setNumTestsPerEvictionRun(3);
        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲链接>最大空闲数 时直接逐出,再也不根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        config.setSoftMinEvictableIdleTimeMillis(1800000);

        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(false);
        //逐出扫描的时间间隔(毫秒) 若是为负数,则不运行逐出线程, 默认-1
        config.setTimeBetweenEvictionRunsMillis(-1);


        return config;
    }

}