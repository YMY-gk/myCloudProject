package com.gk.lock.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**redis可重入自旋锁实现异步续命，防止超时的情况出现，锁被夺走
 * 实现原理：增加一个线程，异步更新该锁的失效时间，直到加锁的线程将锁释放
 *
 * @author : owoYam
 * @date : 2020/12/22 16:54
 */
@Slf4j
public class RedisLockImplAsynTime  {
    @Resource
    private RedisTemplate redisTemplate;
    private static ThreadLocal<String> localUid = new ThreadLocal<String>();
    private static ThreadLocal<Integer> localInteger = new ThreadLocal<Integer>();
    /**
     * 自旋锁的等待时间，1000s后再次尝试获取锁
     */
    private static final long REENTRY_SPIN_SLEEP = 1000;

    public boolean tryLock(String key, long timeout, TimeUnit unit) {
        boolean isLock = false;
        //通过localUid判定本线程是否已经上锁
        if(localUid.get() == null){
            String uuid = UUID.randomUUID().toString();
            localUid.set(uuid);
            while (true){
            //    isLock = redisTemplate.opsForValue().setIfAbsent(key,uuid,timeout,unit);
                if (isLock){
                    break;
                }
                try {
                    Thread.sleep(REENTRY_SPIN_SLEEP);
                } catch (InterruptedException e) {
                    log.error("thread sleep err .......");
                    e.printStackTrace();
                }
            }
            localInteger.set(0);
            new Thread(new AddTimeForLock(key,uuid,redisTemplate)).start();
        }else {
            isLock = true;
        }
        if(isLock){
            //如果已经上锁，则设置重入次数加一
            localInteger.set(localInteger.get()+1);
        }
        return isLock;
    }

    public void releaseLock(String key) {
        if(localUid.get() != null
                && localUid.get().equalsIgnoreCase((String) redisTemplate.opsForValue().get(key))){
            if(localInteger.get() != null && localInteger.get() > 0){}
            //如果已经是本线程，并且已经上锁,锁数量大于0
            localInteger.set(localInteger.get()-1);
        }else {
            //计数器减为0则解锁
            redisTemplate.delete(key);
            localUid.remove();
            localInteger.remove();
        }

    }

}

/**
 * 用于更新过期时间的线程类
 */
@Data
@Slf4j
class AddTimeForLock extends Thread{
    private String key;
    private String uuid;
    private RedisTemplate redisTemplate;

    /**
     * 设置线程睡眠时间为5s
     */
    private static final long SLEEP_TIME = 5000;

    /**
     * 设置锁的过期时间为10s
     */
    private static final long EXPIRE_Time = 10;

    public AddTimeForLock(String key, String uuid, RedisTemplate redisTemplate){
        this.key = key;
        this.uuid = uuid;
        this.redisTemplate = redisTemplate;
    }

    public AddTimeForLock(String key, String uuid){
        this.key = key;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        while (true){
            if(uuid.equalsIgnoreCase((String) redisTemplate.opsForValue().get(key))){
                //如果该锁还没有被释放，则将过期时间往后推10s
                redisTemplate.expire(key,EXPIRE_Time, TimeUnit.SECONDS);
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    log.error("thread sleep err.......");
                    e.printStackTrace();
                }
            }else {
                return;
            }
        }
    }
}