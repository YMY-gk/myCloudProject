package com.gk.lock.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.lock.zookeeper.WatcherManage;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooKeeper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.CountDownLatch;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/8 15:49
 */
@Controller
@Slf4j
public class BeanConfig {


    @Autowired
    private RedisProperties redisProperties;
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用Jackson2JsonRedisSerialize 替换默认的jdkSerializeable序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //   redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        // 1.创建 redisTemplate 模版
        // 2.关联 redisConnectionFactory
        // 3.创建 序列化类
        // 4.设置可见度
        // 5.启动默认的类型
        // 6.序列化类，对象映射设置
        // 7.设置 value 的转化格式和 key 的转化格式

        return redisTemplate;
    }

    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxIdle(redisProperties.getPool().getMaxIdle());
//        config.setMinIdle(redisProperties.getPool().getMaxIdle());
//        config.setMaxTotal(redisProperties.getPool().getMaxActive());
//        config.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
        String host = redisProperties.getHost();
        Integer port = redisProperties.getPort();
        Integer timeout = redisProperties.getTimeout();
        String password = redisProperties.getPassword();
        return new JedisPool(config, host, port, timeout, password);
    }
//    @Bean
//    public RedissonClient redissonClientBean() {
//        Config config = new Config();
//        String host = redisProperties.getHost();
//        Integer port = redisProperties.getPort();
//        Integer timeout = redisProperties.getTimeout();
//        config.useSingleServer().setAddress(host+":"+port);
//        return  Redisson.create(config);
//    }

    @Value("${zookeeper.address}")
    private String connectString;

    @Value("${zookeeper.timeout}")
    private int sessionTimeout;


    @Bean
    public ZooKeeper zkClient() {
        ZooKeeper zooKeeper = null;
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, new WatcherManage(countDownLatch));
            countDownLatch.await();
        } catch (Exception e) {
            log.error(" 初始化Zookeeper连接状态异常: {}",e.getMessage());
        }
        return  zooKeeper;
    }
    @Bean
    public CuratorFramework curatorFramework() {
      RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
      CuratorFramework client = CuratorFrameworkFactory.newClient(connectString,retryPolicy);
      client.start();
      return client;
    }


}
