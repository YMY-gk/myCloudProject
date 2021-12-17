package com.gk.lock.zookeeper;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/29 14:51
 */

import com.gk.lock.utils.RedisDistributionLockPlus;
import com.gk.lock.vo.LockContent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


/**
 * 分布式锁的机制
 * 持久节点，这是zookeeper的默认节点类型，一直存在。
 * 持久顺序节点，创建的节点，zookeeper会依据时间的顺序对创建的节点进行排序。
 * 临时节点，就是在zookeeper中临时创建的节点，zookeeper客户端与服务端断开或者是故障，就会删除临时节点
 * 临时顺序节点，和持久顺序节点类似，只不过就是临时的。
 */
@Slf4j
public class ZKCuratorLock implements Lock {
    /**
     * 加锁路径（）
     */
    private String lockName;
    /**
     * 加锁的现成名
     */
    private String threadName;
    /**
     * zk中节点路径（带编号）
     */
    private String myNode;
    /**
     * 基础路径
     */
    private CuratorFramework zookeeper;
    /**
     * 每个key的过期时间 {@link RedisDistributionLockPlus}
     */
    private static Map<String, LockContent > lockContentMap = new ConcurrentHashMap<>(512);

    public ZKCuratorLock(String lockName, CuratorFramework zk) throws InterruptedException, KeeperException {
        this.lockName=lockName;
        this.threadName = Thread.currentThread().getName();
        this.zookeeper=zk;
        init();
    }

    private void init() throws InterruptedException, KeeperException {
        try {
            Stat stat = new Stat();
                    zookeeper.getData().storingStatIn(stat).forPath(lockName);
            if (stat == null) {
                // 创建根节点
                zookeeper.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(lockName, lockName.getBytes());
            }

        } catch (KeeperException ke) {
            if (KeeperException.Code.NODEEXISTS == ke.code()) {
                System.out.println("锁根目录已经创建 {}"+ lockName);
            } else {
                throw ke;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    @SneakyThrows
    @Override
    public void lock() {
        LockContent lockContentOld = lockContentMap.get(threadName);
        boolean unLocked = null == lockContentOld;
        if (unLocked) {
            if (!tryLock()) {
                throw new Exception("ssss1");
            } else {
                // 将锁放入map
                LockContent lockContent = new LockContent();
                lockContent.setRequestId(lockName);
                lockContent.setThread(Thread.currentThread());
                lockContent.setLockCount(1);
                lockContentMap.put(threadName, lockContent);
            }
        }else{
            // 重复获取锁，在线程池中由于线程复用，线程相等并不能确定是该线程的锁
            if (Thread.currentThread() == lockContentOld.getThread()
                    && lockName.equals(lockContentOld.getRequestId())) {
                // 计数 +1
                lockContentOld.setLockCount(lockContentOld.getLockCount() + 1);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @SneakyThrows
    @Override
    public boolean tryLock() {
        String path = lockName+"/lock";
        myNode = zookeeper.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path, path.getBytes());
        return doJudgeLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        LockContent lockContentOld = lockContentMap.get(threadName);
        Long startTime = System.currentTimeMillis();
        boolean unLocked = null == lockContentOld;
        if (unLocked) {
            if (!tryLock()) {
                return false;
            } else {

                Long endTime = System.currentTimeMillis();
                if ((endTime - startTime) > unit.toMillis(time)) {
                    System.out.println("等待锁超时 {}"+(endTime - startTime));
                    deleteNode();
                    return false;
                }
                // 将锁放入map
                LockContent lockContent = new LockContent();
                lockContent.setRequestId(lockName);
                lockContent.setThread(Thread.currentThread());
                lockContent.setLockCount(1);
                lockContentMap.put(threadName, lockContent);
            }
        }else{
            // 重复获取锁，在线程池中由于线程复用，线程相等并不能确定是该线程的锁
            if (Thread.currentThread() == lockContentOld.getThread()
                    && lockName.equals(lockContentOld.getRequestId())) {
                // 计数 +1
                lockContentOld.setLockCount(lockContentOld.getLockCount() + 1);
            }
        }
        return true;
    }

    @Override
    public void unlock() {
        deleteNode();
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /**
     * 获取所节点
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    private boolean doJudgeLock() throws Exception {
       while (true) {
           System.out.println("查看锁状态{}" + myNode);
           Stat stat = new Stat();
           zookeeper.getData().storingStatIn(stat).forPath(myNode);
           List< String > lockNodes = zookeeper.getChildren().forPath(lockName);
           if (lockNodes != null && lockNodes.size() > 0) {
               //排序取最小值
               Collections.sort(lockNodes);
               String nodeName = myNode.substring(myNode.lastIndexOf("/") + 1);
               System.out.println("判断节点位置，得到当前节点{}" + myNode);
               if (lockNodes.indexOf(nodeName) == -1) {
                   System.out.println("创建后但找不到本节点，网络问题{}" + myNode);
                  continue;
               } else if (lockNodes.indexOf(nodeName) == 0) {
                   System.out.println("得到锁{}" + myNode);
                   return true;
               } else {
                   continue;
               }
           } else {
               System.out.println("创建成功过后却得到空列表");
               return false;
           }
       }
    }

    /**
     * 删除对应节点
     */
    private void deleteNode() {
        try {
            LockContent lockContentOld = lockContentMap.get(threadName);
            boolean unLocked = null == lockContentOld;
            if (lockContentOld.getLockCount()>1 &&Thread.currentThread() == lockContentOld.getThread() && lockName.equals(lockContentOld.getRequestId()))
            {
                lockContentOld.setLockCount(lockContentOld.getLockCount()-1);
                return;
            }
            Stat stat = new Stat();
            zookeeper.getData().storingStatIn(stat).forPath(myNode);
            if(stat != null) {
                zookeeper.delete().forPath(myNode);
                System.out.println("删除节点成功{}"+myNode);
            }
            lockContentMap.remove(threadName);
        } catch (InterruptedException | KeeperException e) {
            System.out.println("删除节点异常"+ e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}