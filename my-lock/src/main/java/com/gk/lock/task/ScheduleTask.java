package com.gk.lock.task;

import com.gk.lock.utils.ReentrantLockUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/9 17:46
 */
public class ScheduleTask implements Runnable {
    public RedisTemplate redisTemplate;
    public ScheduleTask(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public void run() {
        ReentrantLockUtils.task(redisTemplate);

    }
}