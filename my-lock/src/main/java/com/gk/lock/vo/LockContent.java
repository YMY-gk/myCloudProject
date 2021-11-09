package com.gk.lock.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/9 11:45
 */
@Data
@ToString
public  class LockContent implements Serializable {

    /**
     * 锁过期时间，单位秒
     */
    private volatile long lockExpire;

    /**
     * 锁过期时间，单位毫秒
     */
    private volatile long expireTime;

    /**
     * 获取锁的开始时间，单位毫秒
     */
    private volatile long startTime;

    /**
     * 用于防止锁的误删，全局唯一
     */
    private String requestId;

    /**
     * 执行业务的线程
     */
    private volatile Thread thread;

    /**
     * 业务调用设置的锁过期时间，单位秒
     */
    private long bizExpire;

    /**
     * 重入次数
     */
    private int lockCount = 0;

}