package com.gk.company.Filter;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/4/14 11:23
 */
public class RedisShiroCacheManage<K, V> implements Cache<K, V> {
    private String cacheName;
    private RedisTemplate redisTemplate;

    public RedisShiroCacheManage(String cacheName, RedisTemplate redisTemplate) {
        this.cacheName = cacheName;
        this.redisTemplate = redisTemplate;
    }

    //根据Key获取缓存中的值
    public V get(K k) throws CacheException {
        if (ObjectUtil.isNotEmpty(k)) {
            V value = (V) redisTemplate.opsForHash().get(cacheName, k.toString());

            if (value != null) {
                //return (V) SerializationUtils.deserialize(value);
                redisTemplate.expire(cacheName, 10000, TimeUnit.SECONDS);
                return value;
            }
        }
        return null;
    }

    //往缓存中放入key-value，返回缓存中之前的值
    public V put(K k, V v) throws CacheException {
        //   byte[] value = SerializationUtils.serialize(v);
        //先转成JSON对象
        //JSON对象转换为JSON字符串
        //  String jsonString = jsonObject.toJSONString();
        redisTemplate.opsForHash().putIfAbsent(cacheName, k, v);
        redisTemplate.expire(cacheName, 10000, TimeUnit.SECONDS);
        return null;
    }

    //移除缓存中key对应的值，返回该值
    public V remove(K k) throws CacheException {
        return (V) redisTemplate.opsForHash().delete(cacheName, k);
    }

    //清空整个缓存
    public void clear() throws CacheException {
        redisTemplate.delete(cacheName);
    }

    //返回缓存大小
    public int size() {
        return redisTemplate.opsForHash().size(cacheName).intValue();
    }
    //获取缓存中所有的key

    public Set<K> keys() {
        return redisTemplate.opsForHash().keys(cacheName);
    }

    //获取缓存中所有的value
    public Collection<V> values() {
        return redisTemplate.opsForHash().values(cacheName);
    }
}
