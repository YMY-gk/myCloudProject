package com.gk.lock.zookeeper;

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
public class ZookeeperManage {

    ZooKeeper zookeeper = null;

    public ZookeeperManage(String host,Integer timerout) throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        zookeeper = new ZooKeeper(host, timerout, new WatcherManage(latch,"创建"));
        latch.await();
    }

    /** 创建节点，不存在父节点将新增，如果节点已经存在将抛出异常 **/
    public String create(String path, String val) throws KeeperException, InterruptedException {
        if (!checkPath(path)) {
            return "";
        }

        String p = getParentPath(path);
        cycleCreate(p);

        String url = zookeeper.create(path, val.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        return url;
    }

    /** 设置节点的数据，如果节点不存在将新增该节点  **/
    public Stat setData(String path, String val) throws KeeperException, InterruptedException {
        if (!checkPath(path)) {
            System.out.println("---------------------------------------------------");
            return null;
        }

        cycleCreate(path);
        return zookeeper.setData(path, val.getBytes(), -1);
    }

    /** 删除节点，如果存在子节点将递归删除
     * @throws InterruptedException
     * @throws KeeperException **/
    public void delete(String path) throws KeeperException, InterruptedException {
        if (!checkPath(path)) {
            return;
        }

        List<String> chidren = zookeeper.getChildren(path, false);
        for (String p : chidren) {
            delete(path + "/" + p);
        }
        zookeeper.delete(path, -1);
    }

    private void cycleCreate(String path) throws KeeperException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Stat stat = zookeeper.exists(path, new WatcherManage(latch,"查询数据是否存在"));
        if (stat == null) {
            String p = getParentPath(path);
            cycleCreate(p);// 递归
            // 创建
            zookeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    /**
     * 检查目录是否正确
     * @param path
     * @return
     */
    private boolean checkPath(String path) {
        if (!path.startsWith("/")) {
            System.err.println("路径必须以/开头:" + path);
            return false;
        }
        if (path.endsWith("/")) {
            System.err.println("路径不能以/结尾:" + path);
            return false;
        }
        if (path.contains("//")) {
            System.err.println("路径格式不对，存在连续的/:" + path);
            return false;
        }
        if (path.equals("/")) {
            System.err.println("路径格式不对，只有一个/:" + path);
            return false;
        }
        return true;
    }

    /**
     * 获得父级目录
     * @param path /root/abc
     * @return
     */
    private String getParentPath(String path) {
        int index = path.lastIndexOf("/");
        return path.substring(0, index);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZookeeperManage zoo = new ZookeeperManage("182.254.221.85:2181",5000);
        System.out.println(zoo);
        zoo.setData("/lock", "abc");
        zoo.setData("/top/enjoy", "bbb");
        zoo.setData("/top/enjoy/ccc", "ccc");
        System.out.println("成功新增");
        zoo.delete("/top/enjoy");
        System.out.println("成功删除");
    }
}
