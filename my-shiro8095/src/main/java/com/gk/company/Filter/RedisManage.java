package com.gk.company.Filter;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/4/14 14:57
 */

public class RedisManage implements CacheManager {

    private RedisTemplate redisTemplate ;
    public RedisManage(RedisTemplate redisTemplate ){
        this.redisTemplate=redisTemplate;
    }
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        System.out.println("缓存管理器----------------》"+s);
        return new RedisShiroCacheManage<K, V>(s,redisTemplate);
    }
}
