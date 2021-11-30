package com.gk.lock;

import com.gk.lock.task.ScheduleTask;
import com.gk.lock.utils.RedisLockUtils;
import com.gk.lock.utils.ReentrantLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
    public RedissonClient redissonClient;
    @Resource
    public JedisPool jedisPool;

//    @org.junit.Test
//    public void redissonclient() {
//        redissonClient.
//    }

    @org.junit.Test
    public void test() {
        ScheduledExecutorService pool= Executors.newScheduledThreadPool(6);

        ScheduleTask task = new ScheduleTask(redisTemplate);
        pool.schedule(task,1,TimeUnit.SECONDS);
        while(true) {
            try {
                while(true) {
                    if (ReentrantLockUtils.tryGetDistributedLock(redisTemplate, "aaa", "zhangsan+1", 30000l)) {
                        break;
                    } else {
                        System.out.println(Thread.currentThread() + "---------------------------->erer-----:>" + redisTemplate.opsForValue().get("zhangsan"));

                    }
                }
                Thread.currentThread().sleep(1000);
                redisTemplate.opsForValue().increment("zhangsan", 1);
                System.out.println(Thread.currentThread() + "---------------------------->" + redisTemplate.opsForValue().get("zhangsan"));
                break;
              //  this.test02();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
               // ReentrantLockUtils.releaseDistributedLock(redisTemplate, "aaa", "zhangsan+1");
            }
        }
    }
    @org.junit.Test
    public void test02() {
            try {
                while(true) {
                    if (ReentrantLockUtils.tryGetDistributedLock(redisTemplate, "aaa", "zhangsan+1", 1000000000l)) {
                        break;
                    } else {
                        System.out.println(Thread.currentThread() + "---------------------------->erer-----:>" + redisTemplate.opsForValue().get("zhangsan"));

                    }
                }
                Thread.currentThread().sleep(1000);
                redisTemplate.opsForValue().increment("zhangsan", 1);
                System.out.println(Thread.currentThread() + "---------------------------->22" + redisTemplate.opsForValue().get("zhangsan"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ReentrantLockUtils.releaseDistributedLock(redisTemplate, "aaa", "zhangsan+1");
            }

    }
    @org.junit.Test
    public void test01() {
        while(true) {
            try {
                while(true) {
                    if (ReentrantLockUtils.tryGetDistributedLock(redisTemplate, "aaa", "zhangsan+1", 1000000000l)) {
                       break;
                    } else {
                        System.out.println(Thread.currentThread() + "---------------------------->erer-----:>" + redisTemplate.opsForValue().get("zhangsan"));

                    }
                }
                Thread.currentThread().sleep(1000);
                redisTemplate.opsForValue().increment("zhangsan", 1);
                System.out.println(Thread.currentThread() + "---------------------------->" + redisTemplate.opsForValue().get("zhangsan"));
                this.test02();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ReentrantLockUtils.releaseDistributedLock(redisTemplate, "aaa", "zhangsan+1");
            }
        }
    }
}
