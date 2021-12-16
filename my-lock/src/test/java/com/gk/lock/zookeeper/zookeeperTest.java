package com.gk.lock.zookeeper;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/11/29 14:51
 */

import cn.hutool.json.JSONUtil;
import com.gk.lock.LockApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.LockInternals;
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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

    //创建完成注入Bean仓库
    @Resource
    public ZooKeeper zookeeper;
    public  int num=0;
    @Test
    public   void  createZkChanel( ) throws IOException, InterruptedException, KeeperException {
        //创建链接  参数：ip：port   session过期时间（临时节点的数据和session回话有关（在这个时间内不会消失，超过这个回话时间就会消失（存在时间小于回话时间）））
        ZookeeperManage zookeeperManage = new ZookeeperManage("182.254.221.85:2181",100000);
        //手动创建
      //  ZooKeeper zookeeper = zookeeperManage.zookeeper;
        //查询 节点 path:节点路径
        zookeeper.getChildren("/",true,new ZkChildrenCallbackManage(),1);
        List<String> childrenList=zookeeper.getChildren("/",true);
        List<String> childrenList1=zookeeper.getChildren("/",false);

        System.out.println("----------------children:"+ JSONUtil.toJsonStr(childrenList));
        System.out.println("----------------children1:"+ JSONUtil.toJsonStr(childrenList1));
        CountDownLatch latch = new CountDownLatch(0);
        List<String> childrenList2=zookeeper.getChildren("/",new WatcherManage(latch,"查询节点"));
        System.out.println("----------------children2:"+ JSONUtil.toJsonStr(childrenList2));
        latch.await();
        latch = new CountDownLatch(0);
        //创建节点
        String createResult=zookeeper.create("/node1","节点值".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //获取节点数据
        byte[] result= zookeeper.getData("/node1",true,null);
        System.out.println("----------------getData:"+ new String(result));
        //修改节点
        Stat stat=zookeeper.setData("/node1","同步修改".getBytes(),-1);
        System.out.println("----------------stat:"+ stat.toString());

        result= zookeeper.getData("/node1",true,null);
        System.out.println("----------------getData:"+ new String(result));
        //判断节点存在
        stat =zookeeper.exists("/node1",true);
        //删除节点
        zookeeper.delete("/node1",-1);
        latch.await();
//
        System.out.println("----------------stat1:"+ stat.toString());

    }
    @Test
    public   void  zkLock( ) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);

        Thread a = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        ZKLock lock = null;
                        try {
                            //我的zookeeper地址:192.168.40.204:2181
                            lock = new ZKLock("/locks/test", zookeeper);
                            sleep(10000);
                            lock.lock();
                            System.out.println("a得到锁了，xiumian -----------------"+(num++));
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("a获锁失败"+e);
                        } finally {
                            System.out.println("a释放锁前------------------------------ ");
                            lock.unlock();
                            System.out.println("a释放锁了，xiumian ");
                            latch.countDown();
                        }
                    }
                }
        );

        Thread b = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        ZKLock lock = null;
                        try {
                            sleep(1000);
                            lock = new ZKLock("/locks/test", zookeeper);
                            lock.lock();
                            sleep(10000);
                            System.out.println("b得到锁了-----------------"+(num++));

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("b获锁失败"+ e);
                        } finally {
                            System.out.println("b释放锁前------------------------------ ");
                            lock.unlock();
                            System.out.println("b释放锁了 ");
                            latch.countDown();

                        }
                    }
                }
        );

        Thread c = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        ZKLock lock = null;
                        try {
                            sleep(1000);
                            lock = new ZKLock("/locks/test", zookeeper);
                            lock.lock();
                            sleep(10000);
                            System.out.println("c得到锁了-----------------"+(num++));
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("c获锁失败"+ e);
                        } finally {
                            System.out.println("c释放锁前------------------------------ ");
                            lock.unlock();
                            System.out.println("c释放锁了 ");
                            latch.countDown();

                        }
                    }
                }
        );

        Thread d = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        ZKLock lock = null;
                        try {
                            sleep(1000);
                            lock = new ZKLock("/locks/test", zookeeper);
                            lock.lock();
                            sleep(10000);
                            System.out.println("d得到锁了-----------------"+(num++));
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("d获锁失败"+e);
                        } finally {
                            System.out.println("d释放锁前------------------------------ ");
                            lock.unlock();
                            System.out.println("d释放锁了 ");
                            latch.countDown();

                        }
                    }
                }
        );

        Thread e = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        ZKLock lock = null;
                        try {
                            sleep(1000);
                            lock = new ZKLock("/locks/test", zookeeper);
                            lock.lock();
                            sleep(10000);
                            System.out.println("e得到锁了-----------------"+(num++));
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("e获锁失败"+e);
                        } finally {
                            System.out.println("e释放锁前------------------------------ ");
                            lock.unlock();
                            System.out.println("e释放锁了 ");
                            latch.countDown();

                        }
                    }
                }
        );
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
        latch.await();

      ///  while (true);
    }

}