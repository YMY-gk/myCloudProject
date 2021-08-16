package com.gk.company.Filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/4/19 20:43
 */
@Component
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 创建session，保存到redis数据库
     *
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        try {
            redisTemplate.opsForValue().setIfAbsent("shiro:session:"+sessionId.toString(), session);
            redisTemplate.expire("shiro:session:"+sessionId.toString(),3600L, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionId;
    }
    /**
     * 获取session
     *
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            try {
                session = (Session)redisTemplate.opsForValue().get("shiro:session:"+sessionId.toString());
                redisTemplate.expire("shiro:session:"+sessionId.toString(),3600L, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return session;
    }
    /**
     * 更新session的最后一次访问时间
     *
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        try {
            redisTemplate.opsForValue().setIfAbsent("shiro:session:"+session.getId().toString(), session);
            redisTemplate.expire("shiro:session:"+session.getId().toString(),3600L, TimeUnit.SECONDS);        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除session
     *
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        try {
            redisTemplate.delete("shiro:session:"+session.getId().toString());
            redisTemplate.expire("shiro:session:"+session.getId().toString(),1L, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
