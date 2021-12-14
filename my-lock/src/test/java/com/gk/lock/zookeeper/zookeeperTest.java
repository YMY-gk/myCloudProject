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

    public   void  createZkChanel(String host,String timerout,String pathSeperator ) throws IOException, InterruptedException {

    }

}