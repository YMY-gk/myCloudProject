package com.gk.lock.utils;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/29 14:51
 */

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.ZooKeeper;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ZookeeperLock{

    private ZkClient zkClient;

    private static final String ROOT_PATH="/lock";

    private String lockName;
    private String eNodeName;
    private String eNodeNo;

    public  ZookeeperLock(String lockName){
        zkClient = new ZkClient("182.254.221.85:2181",2000);
        //连接zk
        this.lockName =ROOT_PATH.concat("/").concat(lockName);
        //创建持久节点，该节点下后面创建临时有序节点
        if (!zkClient.exists(this.lockName)) {
            zkClient.createPersistent(this.lockName);
        }
    }

    public void lock(){
        for (;;) {
            if (eNodeName == null) {
                //创建临时有序节点
                eNodeName = zkClient.createEphemeralSequential(lockName.concat("/"), System.currentTimeMillis());
                eNodeNo=eNodeName.substring(eNodeName.lastIndexOf("/")+1);
            }
            List<String> children = zkClient.getChildren(lockName);
            //节点排序
            Collections.sort(children);
            if (children.size() > 0) {
                if (children.get(0).equals(eNodeNo)) {
                    //自己创建的就是第一个临时顺序节点，意味着拿到了锁
                    System.out.println(eNodeNo+"------------》获得了锁");
                    break;
                }else {
                    CountDownLatch latch = new CountDownLatch(1);
                    //获取改节点的前一个节点，注册监听事件，并等待
                    String prevPath= children.get(children.indexOf(eNodeNo)-1);
                    System.out.println(eNodeNo+"注册监听到------》"+prevPath);
                    zkClient.subscribeDataChanges(lockName.concat("/").concat(prevPath), new IZkDataListener() {
                        @Override
                        public void handleDataChange(String s, Object o) throws Exception {
                            latch.countDown();
                        }

                        @Override
                        public void handleDataDeleted(String s) throws Exception {
                            System.out.println(eNodeNo+"！！！！！！！！！！！被唤醒");
                            latch.countDown();
                        }
                    });

                    try {
                        System.out.println(eNodeNo+"??????????????开始等待");
                        latch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }else {
                //异常情况
                System.out.println("子节点数据为空，抛出异常");
                throw new RuntimeException("获取锁异常");
            }
        }
    }

    public void unLock(){
        zkClient.delete(eNodeName);
        zkClient.close();
        System.out.println(eNodeNo+"********************释放了锁");
    }

    public static void main(String[] args) {
        //测试5个线程的争抢
        int threadCount=5;
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(()->{
                try {
                    latch.await();
                    ZookeeperLock lock = new ZookeeperLock("lockTest");
                    lock.lock();
                    //模拟处理业务逻辑
                    Thread.sleep(1000+ new Random().nextInt(100)*10);
                    lock.unLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            latch.countDown();
        }


    }
}