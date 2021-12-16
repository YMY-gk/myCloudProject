package com.gk.lock.zookeeper;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/29 14:51
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import static java.lang.Thread.sleep;


/**
 * 分布式锁的机制
 */
@Slf4j
public class DistributedLock implements Lock, Watcher {

    private static final int SESSION_TIMEOUT = 2000000*100;
    private String lockName;
    private String root = "/locks";
    private String pathSeperator = "/";
    private String idSeperator = "-";
    private String lockPath;
    private String waitNode;
    private String myNode;
    private CountDownLatch latch;
    private String host;
    private static int num=1;

    protected ZooKeeper zk;
    private CountDownLatch connectedSignal = new CountDownLatch(1);

    public DistributedLock(String host, String lockName) throws IOException, InterruptedException, KeeperException {
        this.host = host;
        zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
        this.lockName = lockName;
        lockPath = root + pathSeperator + lockName;
        connectedSignal.await();
        init();

    }

    private void init() throws InterruptedException, KeeperException {
        try {
            Stat stat = zk.exists(root, false);
            if (stat == null) {
                // 创建根节点
                zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

        } catch (KeeperException ke) {
            if (KeeperException.Code.NODEEXISTS == ke.code()) {
                System.out.println("锁根目录已经创建 {}"+ root);
            } else {
                throw ke;
            }
        }
        try {
            Stat stat = zk.exists(lockPath, false);
            if (stat == null) {
                // 创建根节点
                zk.create(lockPath, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                System.out.println("创建锁节点 {} 成功"+lockPath);
            } else {
                System.out.println("无需创建锁目录{}"+ lockPath);
            }
        } catch (KeeperException ke) {
            if (KeeperException.Code.NODEEXISTS == ke.code()) {
                System.out.println("无需创建锁目录 {}"+ lockPath);
            } else {
                throw ke;
            }
        }
    }


    @Override
    public void process(WatchedEvent event) {
        log.info("-----------------------------------------监听事件的状态: {}",event.getState());
        log.info("-----------------------------------------监听事件的路径: {}",event.getPath());
        log.info("-----------------------------------------监听事件的类型: {}",event.getType());
        if (event.getState() == Event.KeeperState.SyncConnected) {
            connectedSignal.countDown();
            return;
        }
        if (event.getState() == Event.KeeperState.Expired) {
            try {
               System.out.println("zk session 超时，开始重连");
                zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
            } catch (IOException e) {
              //  log.error("zk session 超时，重连失败",e);
            }
            return;
        }

        if (event.getType() == Event.EventType.NodeDeleted && this.latch != null) {
            this.latch.countDown();
        }
    }


    @Override
    public void lock() {
        if (tryLock()) {
            return;
        } else {
            waitForLock(-1L, null);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }


    @Override
    public boolean tryLock() {
        String requiredPath = lockPath + pathSeperator +"lock" + idSeperator;
        try {
            myNode = zk.create(requiredPath, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
            System.out.println("创建节点成功{}"+myNode);
        } catch (KeeperException | InterruptedException e) {
            System.out.println("创建节点异常{}"+ myNode+e);
            return false;
        }
        return judgeLock();

    }


    @Override
    public void unlock() {
        deleteNode();
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                System.out.println("关闭连接异常"+e);
            }
        }
    }


    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (this.tryLock()) {
            return true;
        } else {
            return waitForLock(time, unit);
        }

    }


    private boolean doJudgeLock() throws KeeperException, InterruptedException {
        List<String> lockNodes = zk.getChildren(lockPath, false);
        sleep(1000);

        if (lockNodes != null && lockNodes.size() > 0) {
            Collections.sort(lockNodes);
            String nodeName = myNode.substring(myNode.lastIndexOf("/")+1);
            System.out.println("判断节点位置，得到当前节点{}"+ myNode);

            if (lockNodes.indexOf(nodeName) == -1) {
                System.out.println("创建后但找不到本节点，网络问题{}"+myNode);
                return false;
            } else if (lockNodes.indexOf(nodeName) == 0) {
                System.out.println("得到锁{}"+myNode);
                return true;
            } else {
                waitNode = lockPath+pathSeperator+lockNodes.get(lockNodes.indexOf(nodeName)-1);
                System.out.println("得到前置节点{}"+waitNode);
                return false;
            }
        } else {
            System.out.println("创建成功过后却得到空列表");
            return false;
        }
    }



    private boolean judgeLock() {
        try {
            return doJudgeLock();
        } catch (KeeperException | InterruptedException e) {
            System.out.println("获取分布式锁错误"+e);
            return false;
        }
    }


    private boolean waitForLock(Long time, TimeUnit unit) {
        Stat stat;
        try {
            System.out.println("查看锁状态{}"+waitNode);
            latch = new CountDownLatch(1);
            final Watcher previousListener = new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("监听到前置节点释放信息{}"+waitNode);

                    if (event.getType() == Event.EventType.NodeDeleted) {
                        System.out.println("监听到前置节点释放信息{}"+waitNode);
                        if(latch != null) {
                            latch.countDown();
                            latch = null;
                        } else {
                            System.out.println("未触发latch");
                        }
                    }
                }
            };
            stat = zk.exists(waitNode, previousListener);
        } catch (KeeperException | InterruptedException e) {
           // log.error("等待锁zk错误", e);
            return false;
        }

        if (stat != null) {
            try {
                System.out.println("监听锁，等待前置节点{} 事件,"+ waitNode);
                long startTime = System.currentTimeMillis();
                if (time > 0) {
                    latch.await(time, unit);
                    long endTime = System.currentTimeMillis();
                    if ((endTime - startTime) > unit.toMillis(time)) {
                        System.out.println("等待锁超时 {}"+(endTime - startTime));
                        deleteNode();
                        return false;
                    } else {
                        return judgeLock();
                    }
                } else {
                    latch.await();
                    return judgeLock();
                }
            } catch (InterruptedException e) {
                System.out.println("等待锁过程中异常"+ e);
                return false;
            }
        } else {
            System.out.println("获取前置节点存在信息为null"+waitNode);
            return true;
        }
    }

    private void deleteNode() {
        try {
            Stat stat = zk.exists(myNode,false) ;
            if(stat != null) {
                zk.delete(myNode, -1);
                System.out.println("删除节点成功{}"+myNode);
            }
        } catch (InterruptedException | KeeperException e) {
            System.out.println("删除节点异常"+ e);
        }
    }


    public static void main(String[] args) {

        Thread a = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        DistributedLock lock = null;
                        try {
                            //我的zookeeper地址:192.168.40.204:2181
                            lock = new DistributedLock("182.254.221.85:2181", "testlock");
                            sleep(10000);
                            lock.lock();
                            System.out.println("a得到锁了，xiumian -----------------"+(num++));
                        } catch (IOException e) {
                            System.out.println("a获锁失败"+e);
                        } catch (InterruptedException e) {
                            System.out.println("a获锁失败"+e);
                        } catch (KeeperException e) {
                            System.out.println("a获锁失败"+e);
                        } finally {
                            System.out.println("a释放锁前------------------------------ ");
                            lock.unlock();
                            System.out.println("a释放锁了，xiumian ");
                        }
                    }
                }
        );

        Thread b = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        DistributedLock lock = null;
                        try {
                            sleep(1000);
                            lock = new DistributedLock("182.254.221.85:2181", "testlock");
                            lock.lock();
                            sleep(10000);
                            System.out.println("b得到锁了-----------------"+(num++));

                        } catch (IOException e) {
                            System.out.println("b获锁失败"+ e);
                        } catch (InterruptedException e) {
                            System.out.println("b获锁失败"+ e);
                        } catch (KeeperException e) {
                            System.out.println("b获锁失败"+ e);
                        } finally {
                            System.out.println("b释放锁前------------------------------ ");
                            lock.unlock();
                            System.out.println("b释放锁了 ");
                        }
                    }
                }
        );

        Thread c = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        DistributedLock lock = null;
                        try {
                            sleep(1000);
                            lock = new DistributedLock("182.254.221.85:2181", "testlock");
                            lock.lock();
                            sleep(10000);
                            System.out.println("c得到锁了-----------------"+(num++));
                        } catch (IOException e) {
                            System.out.println("c获锁失败"+e);
                        } catch (InterruptedException e) {
                            System.out.println("c获锁失败"+ e);
                        } catch (KeeperException e) {
                            System.out.println("c获锁失败"+ e);
                        } finally {
                            System.out.println("c释放锁前------------------------------ ");
                            lock.unlock();
                            System.out.println("c释放锁了 ");
                        }
                    }
                }
        );

        Thread d = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        DistributedLock lock = null;
                        try {
                            sleep(1000);
                            lock = new DistributedLock("182.254.221.85:2181", "testlock");
                            lock.lock();
                            sleep(10000);
                            System.out.println("d得到锁了-----------------"+(num++));
                        } catch (IOException e) {
                            System.out.println("d获锁失败"+e);
                        } catch (InterruptedException e) {
                            System.out.println("d获锁失败"+e);
                        } catch (KeeperException e) {
                            System.out.println("d获锁失败"+e);
                        } finally {
                            System.out.println("d释放锁前------------------------------ ");
                            lock.unlock();
                            System.out.println("d释放锁了 ");
                        }
                    }
                }
        );

        Thread e = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        DistributedLock lock = null;
                        try {
                            sleep(1000);
                            lock = new DistributedLock("182.254.221.85:2181", "testlock");
                            lock.lock();
                            sleep(10000);
                            System.out.println("e得到锁了-----------------"+(num++));
                        } catch (IOException e) {
                            System.out.println("e获锁失败"+e);
                        } catch (InterruptedException e) {
                            System.out.println("e获锁失败"+e);
                        } catch (KeeperException e) {
                            System.out.println("e获锁失败"+ e);
                        } finally {
                            System.out.println("e释放锁前------------------------------ ");
                            lock.unlock();
                            System.out.println("e释放锁了 ");
                        }
                    }
                }
        );
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();

    }

}