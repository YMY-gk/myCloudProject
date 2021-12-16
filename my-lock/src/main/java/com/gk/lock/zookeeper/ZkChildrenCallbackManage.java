package com.gk.lock.zookeeper;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 获取对应层级下数据
 * @author guokui
 * @class myCloudProject
 * @date 2021/12/14 17:49
 */
public class ZkChildrenCallbackManage implements AsyncCallback.ChildrenCallback  {

    @Override
    public void processResult(int rc, String path, Object ctx, List< String > children) {
        System.out.println("rc:"+rc+",path:"+path+",ctx:"+ctx+",children:"+ JSONUtil.toJsonStr(children));
    }
}
