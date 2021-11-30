package com.gk.lock.severce;

import com.gk.lock.severce.impl.ZooKeeperLock;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/15 11:29
 */
public class NodeBlocklessLock implements ZooKeeperLock {

    /** 尝试获取锁 */

    @Override
    public boolean lock(String guidNodeName, String clientGuid) {
        return true;
    }



    /** 释放锁 */

    @Override
    public boolean release(String guidNodeName, String clientGuid) {
        return true;

    }



    /** 锁是否已经存在 */

    @Override
    public boolean exists(String guidNodeName) {
        return true;

    }

}