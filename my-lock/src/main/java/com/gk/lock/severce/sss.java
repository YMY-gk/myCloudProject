package com.gk.lock.severce;

import com.gk.lock.severce.impl.ZooKeeperLock;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/15 11:29
 */
public class NodeBlocklessLock extends ZooKeeperBase implements ZooKeeperLock {

    /** 尝试获取锁 */

    public boolean lock(String guidNodeName, String clientGuid) {

        boolean result = false;

        if (getZooKeeper().exists(guidNodeName, false) == null) {

            getZooKeeper().create(guidNodeName, clientGuid.getBytes(),

                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

            byte[] data = getZooKeeper().getData(guidNodeName, false, null);

            if (data != null && clientGuid.equals(new String(data))) {

                result = true;

            }

        }

        return result;

    }



    /** 释放锁 */

    public boolean release(String guidNodeName, String clientGuid) {

        boolean result = false;

        Stat stat = new Stat();

        byte[] data = getZooKeeper().getData(guidNodeName, false, stat);

        if (data != null && clientGuid.equals(new String(data))) {

            getZooKeeper().delete(guidNodeName, stat.getVersion());

            result = true;

        }

        return result;

    }



    /** 锁是否已经存在 */

    public boolean exists(String guidNodeName) {

        boolean result = false;

        Stat stat = getZooKeeper().exists(guidNodeName, false);

        result = stat != null;

        return result;

    }

}