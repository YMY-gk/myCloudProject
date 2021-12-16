package com.gk.lock.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/12/14 17:49
 */
@Slf4j
public class WatcherManage implements Watcher {

    public  CountDownLatch latch ;
    public  String cxt ;

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(cxt+"=>通知: 进入监听");
        log.info("监听事件的状态: {}",watchedEvent.getState());
        log.info("监听事件的路径: {}",watchedEvent.getPath());
        log.info("监听事件的类型: {}",watchedEvent.getType());
        /**
         * 节点有数据变化
         */
        if (watchedEvent.getState() == Event.KeeperState.AuthFailed) {//节点有数据变化
            System.out.println("===========Auth failed state===========");
            if (watchedEvent.getType()== Event.EventType.None){
                System.out.println("===========认证失败===========");
            }

        }else if (watchedEvent.getState() == Event.KeeperState.Expired) {//
            System.out.println("===========session timeout===========");
        }else if (watchedEvent.getState() == Event.KeeperState.Disconnected) {//
            System.out.println("===========The client is in the disconnected state===========");
        }else if(watchedEvent.getState().equals(Event.KeeperState.SyncConnected)){
            if (watchedEvent.getType()== Event.EventType.None){
                System.out.println("===========连接成功===========");
            }
            else if(watchedEvent.getType()== Event.EventType.NodeCreated){
                System.out.println("=>通知:节点创建"+watchedEvent.getPath());
            }else if(watchedEvent.getType()== Event.EventType.NodeDataChanged){
                System.out.println("=>通知：节点修改"+watchedEvent.getPath());
            }else if(watchedEvent.getType()== Event.EventType.NodeDeleted){
                System.out.println("=>通知：节点删除"+watchedEvent.getPath());
            }else if(watchedEvent.getType()== Event.EventType.NodeChildrenChanged){
                System.out.println("=>通知：子节点删除"+watchedEvent.getPath());
            }
        }
        if (latch!=null&&latch.getCount()>0) {
            latch.countDown();
        }
        System.out.println(cxt+"=>通知: 退出监听");

    }
    public WatcherManage(CountDownLatch latch ){
        this.latch=latch;
    }
    public WatcherManage(CountDownLatch latch,String cxt){
        this.latch=latch;
        this.cxt=cxt;
    }
}
