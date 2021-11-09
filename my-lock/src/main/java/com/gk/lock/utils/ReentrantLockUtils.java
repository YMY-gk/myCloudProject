package com.gk.lock.utils;

import com.gk.lock.vo.LockContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/8 14:16
 */
@Slf4j
public class ReentrantLockUtils {

    /**
     * 加锁超时时间，单位毫秒， 即：加锁时间内执行完操作，如果未完成会有并发现象
     */
    private static final long DEFAULT_LOCK_TIMEOUT = 30;
    /**
     * 获取锁超时中断
     */
    private static final long acquire_LOCK_TIMEOUT = 30;
    /**
     * 业务没有执行完成时增加过期时间
     */
    private static final long TIME_SECONDS_FIVE = 5 ;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    /**
     * 每个key的过期时间 {@link RedisDistributionLockPlus}
     */
    private static Map<String, LockContent > lockContentMap = new ConcurrentHashMap<>(512);
    /**
     * 获取锁lua脚本， k1：获锁key, k2：续约耗时key, arg1:requestId，arg2：超时时间
     */
    private static final String LOCK_SCRIPT = "if redis.call('exists', KEYS[2]) == 1 then ARGV[2] = math.floor(redis.call('get', KEYS[2]) + 10) end " +
            "if redis.call('exists', KEYS[1]) == 0 then " +
            "local t = redis.call('set', KEYS[1], ARGV[1], 'EX', ARGV[2]) " +
            "for k, v in pairs(t) do " +
            "if v == 'OK' then return tonumber(ARGV[2]) end " +
            "end " +
            "return 0 end";

    /**
     * 释放锁lua脚本, k1：获锁key, k2：续约耗时key, arg1:requestId，arg2：业务耗时 arg3: 业务开始设置的timeout
     */
//    private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
//            "local ctime = tonumber(ARGV[2]) " +
//            "local biz_timeout = tonumber(ARGV[3]) " +
//            "if ctime > 0 then  " +
//            "if redis.call('exists', KEYS[2]) == 1 then " +
//            "local avg_time = redis.call('get', KEYS[2]) " +
//            "avg_time = (tonumber(avg_time) * 8 + ctime * 2)/10 " +
//            "if avg_time >= biz_timeout - 5 then redis.call('set', KEYS[2], avg_time, 'EX', 24*60*60) " +
//            "else redis.call('del', KEYS[2]) end " +
//            "elseif ctime > biz_timeout -5 then redis.call('set', KEYS[2], ARGV[2], 'EX', 24*60*60) end " +
//            "end " +
//            "return redis.call('del', KEYS[1]) " +
//            "else return 0 end";
    private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private static final String RENEW_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('expire', KEYS[1], ARGV[2]) else return 0 end";


    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @return 是否获取成功
     */
    public static  boolean tryGetDistributedLock( RedisTemplate redisTemplate, String lockKey, String requestId, Long expire) {


            //3、记录获取到锁的线程+1
            //4、返回true

        log.info("开始执行加锁, lockKey ={}, requestId={}", lockKey, requestId);
        //1、 判断是否已经有线程持有锁，减少redis的压力
        LockContent lockContentOld = lockContentMap.get(lockKey);
        boolean unLocked = null == lockContentOld;
        if (unLocked) {
            long timeMillis = System.currentTimeMillis();

            for (; ; ) {
                //2、不存在直接加锁
                long startTime = System.currentTimeMillis();
                // 计算超时时间
                long bizExpire = expire == 0L ? DEFAULT_LOCK_TIMEOUT : expire;
                String lockKeyRenew = lockKey + "_renew";

                RedisCallback< String > callback = (connection) -> {
                    JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                    return commands.set(lockKeyRenew, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, bizExpire);
                };
                String result = (String) redisTemplate.execute(callback);
                if (!StringUtils.isEmpty(result)) {
                    // 将锁放入map
                    LockContent lockContent = new LockContent();
                    lockContent.setStartTime(startTime);
                    lockContent.setLockExpire(bizExpire);
                    lockContent.setExpireTime(startTime + bizExpire * 1000);
                    lockContent.setRequestId(requestId);
                    lockContent.setThread(Thread.currentThread());
                    lockContent.setBizExpire(bizExpire);
                    lockContent.setLockCount(1);
                    lockContentMap.put(lockKey, lockContent);
                    log.info("加锁成功, lockKey ={}, requestId={}", lockKey, requestId);
                    break;
                }
                if ((startTime-timeMillis)>acquire_LOCK_TIMEOUT*1000){
                    return false;
                }
            }
        }else {
            // 重复获取锁，在线程池中由于线程复用，线程相等并不能确定是该线程的锁
            if (Thread.currentThread() == lockContentOld.getThread()
                    && requestId.equals(lockContentOld.getRequestId())) {
                // 计数 +1
                lockContentOld.setLockCount(lockContentOld.getLockCount() + 1);
            }
        }
            // 如果被锁或获取锁失败，则等待100毫秒
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                // 这里用lombok 有问题
                log.error("获取redis 锁失败, lockKey ={}, requestId={}", lockKey, requestId, e);
                return false;
            }
        return  true;

    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(  RedisTemplate redisTemplate,String lockKey, String requestId) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            //1、判断当前线程锁冲入次数
            //2、当次数大一时直接减一
            //3判断当前加锁数，小于等于0时直接关闭锁
            String lockKeyRenew = lockKey + "_renew";
            LockContent lockContent = lockContentMap.get(lockKey);
            long consumeTime;
            if (requestId.equals(lockContent.getRequestId())) {
                int lockCount = lockContent.getLockCount();
                // 每次释放锁， 计数 -1，减到0时删除redis上的key
                if (--lockCount > 0) {
                    lockContent.setLockCount(lockCount);
                    return true;
                }else{
                    List<String> keys = new ArrayList<>();
                    keys.add(lockKeyRenew);
                    List<String> args = new ArrayList<>();
                    args.add(requestId);
                    // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
                    // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
                    RedisCallback<Long> callback = (connection) -> {
                        Object nativeConnection = connection.getNativeConnection();
                        // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                        // 集群模式
                        if (nativeConnection instanceof JedisCluster) {
                            return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_SCRIPT, keys, args);
                        }
                        // 单机模式
                        else if (nativeConnection instanceof Jedis) {
                            return (Long) ((Jedis) nativeConnection).eval(UNLOCK_SCRIPT, keys, args);
                        }
                        //0L是失败
                        return 0L;
                    };
                    Long result = (Long) redisTemplate.execute(callback);
                    // 删除已完成key，先删除本地缓存，减少redis压力, 分布式锁，只有一个，所以这里不加锁
                    lockContentMap.remove(lockKey);
                    return true;
                }
            } else {
                log.info("释放锁失败，不是自己的锁。");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }

    public static void task(RedisTemplate redisTemplate ) {
        Set<Map.Entry<String, LockContent >> entries = lockContentMap.entrySet();
        for (Map.Entry<String, LockContent > entry : entries) {
            String lockKey = entry.getKey();
            LockContent lockContent = entry.getValue();
            long expireTime = lockContent.getExpireTime();
            // 减少线程池中任务数量
            if ((expireTime - System.currentTimeMillis())/ 1000 < TIME_SECONDS_FIVE) {
                //线程池异步续约
                    boolean renew = renew(redisTemplate ,lockKey, lockContent);
                    if (renew) {
                        long expireTimeNew = lockContent.getStartTime() + (expireTime - lockContent.getStartTime()) * 2 - TIME_SECONDS_FIVE * 1000;
                        lockContent.setExpireTime(expireTimeNew);
                    } else {
                        // 续约失败，说明已经执行完 OR redis 出现问题
                        lockContentMap.remove(lockKey);
                    }
            }
        }
    }

    /**
     * 续约
     *
     * @param lockKey
     * @param lockContent
     * @return true:续约成功，false:续约失败（1、续约期间执行完成，锁被释放 2、不是自己的锁，3、续约期间锁过期了（未解决））
     */
    public static boolean renew(  RedisTemplate redisTemplate ,String lockKey, LockContent lockContent) {
        log.info("执行业务的线程已终止,不再续约 lockKey ={}, lockContent={}", lockKey, lockContent);

        // 检测执行业务线程的状态
        Thread.State state = lockContent.getThread().getState();
        if (Thread.State.TERMINATED == state) {
            log.info("执行业务的线程已终止,不再续约 lockKey ={}, lockContent={}", lockKey, lockContent);
            return false;
        }

        String requestId = lockContent.getRequestId();
        long timeOut = (lockContent.getExpireTime() - lockContent.getStartTime()) / 1000;

        String lockKeyRenew = lockKey + "_renew";
        List<String> keys = new ArrayList<>();
        keys.add(lockKeyRenew);
        List<String> args = new ArrayList<>();
        args.add(requestId);
        args.add(Long.toString(timeOut));
        // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
        // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
        RedisCallback<Long> callback = (connection) -> {
            Object nativeConnection = connection.getNativeConnection();
            // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
            // 集群模式
            if (nativeConnection instanceof JedisCluster) {
                return (Long) ((JedisCluster) nativeConnection).eval(RENEW_SCRIPT, keys, args);
            }
            // 单机模式
            else if (nativeConnection instanceof Jedis) {
                return (Long) ((Jedis) nativeConnection).eval(RENEW_SCRIPT, keys, args);
            }
            //0L是失败
            return 0L;
        };
        Long result = (Long) redisTemplate.execute(callback);
        log.info("续约结果，True成功，False失败 lockKey ={}, result={}", lockKey, result);
        return 1L==result;
    }
}
