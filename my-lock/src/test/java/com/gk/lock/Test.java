package com.gk.lock;

import com.gk.lock.utils.RedisLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/8 13:55
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LockApplication.class})
public class Test {
    @Resource
    public RedisTemplate redisTemplate;
    @Resource
    public JedisPool jedisPool;

    @org.junit.Test
    public void test() {
        redisTemplate.opsForValue().set("zhangsan", 1, 100000, TimeUnit.MINUTES);
        while(true) {
            try {
                while(true) {
                    if (RedisLockUtils.tryGetDistributedLock(redisTemplate, "aaa", "zhangsan+1", 1000000000)) {
                        break;
                    } else {
                        System.out.println(Thread.currentThread() + "---------------------------->erer-----:>" + redisTemplate.opsForValue().get("zhangsan"));

                    }
                }
                redisTemplate.opsForValue().increment("zhangsan", 1);
                System.out.println(Thread.currentThread() + "---------------------------->" + redisTemplate.opsForValue().get("zhangsan"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                RedisLockUtils.releaseDistributedLock(redisTemplate, "aaa", "zhangsan+1");
            }
        }
    }

    @org.junit.Test
    public void test01() {
        while(true) {
            try {
                while(true) {
                    if (RedisLockUtils.tryGetDistributedLock(redisTemplate, "aaa", "zhangsan+1", 1000000000)) {
                       break;
                    } else {
                        System.out.println(Thread.currentThread() + "---------------------------->erer-----:>" + redisTemplate.opsForValue().get("zhangsan"));

                    }
                }
                redisTemplate.opsForValue().increment("zhangsan", 1);
                System.out.println(Thread.currentThread() + "---------------------------->" + redisTemplate.opsForValue().get("zhangsan"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                RedisLockUtils.releaseDistributedLock(redisTemplate, "aaa", "zhangsan+1");
            }
        }
    }
}
