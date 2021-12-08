package com.gk.lock.zookeeper;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/29 14:51
 */

import com.gk.lock.LockApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.LockInternals;
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LockApplication.class})
public class zookeeperTest{

    /**
     * 链接超时时间
     */
    private static final int SESSION_TIMEOUT = 2000*100;
    /**
     * 初始创建路径
     */
    private String pathSeperator = "/";
    private String host ="182.254.221.85:2181";

    public  ZooKeeper createZkChanel(String host,String timerout ) throws IOException, InterruptedException {
        /**
         * 等待线程执行完成
         *
         */
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Watcher watcher= new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                /**
                 * 节点有数据变化
                 */
                if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {//节点有数据变化

                }else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {//
                }
                try {
                    if(watchedEvent.getState().equals(Watcher.Event.KeeperState.SyncConnected)){
                        if (watchedEvent.getType()== Event.EventType.None){
                            System.out.println("===========连接成功===========");
                        }
                        else if(watchedEvent.getType()== Watcher.Event.EventType.NodeCreated){
                            System.out.println("=>通知:节点创建"+watchedEvent.getPath());
                        }else if(watchedEvent.getType()== Watcher.Event.EventType.NodeDataChanged){
                            System.out.println("=>通知：节点修改"+watchedEvent.getPath());
                        }else if(watchedEvent.getType()== Watcher.Event.EventType.NodeDeleted){
                            System.out.println("=>通知：节点删除"+watchedEvent.getPath());
                        }else if(watchedEvent.getType()== Watcher.Event.EventType.NodeChildrenChanged){
                            System.out.println("=>通知：子节点删除"+watchedEvent.getPath());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

        }
        ZooKeeper zk  = new ZooKeeper(host,SESSION_TIMEOUT,watcher);
        countDownLatch.await();
        zk.create()
        return zk;
    }

}