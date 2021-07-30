package com.gk.design.lintcode.tread.MainSubThread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.IntConsumer;

public class Solution {
    static  volatile int aa = 1;

    public void printNumberInMainSubThread(int n, IntConsumer intConsumer) throws InterruptedException {
        // write your code here
         new Thread(() -> {
            for (; aa <= n; ) {
                int num = aa % 30;
                if (num >= 11 && num <= 30 || num == 0){
                    synchronized (Solution.class) {
                        intConsumer.accept(aa);
                        aa++;
                    }
                }
            }
        }).start();
        for (; aa <= n; ) {
            int num = aa % 30;
            if (num >= 1 && num <= 10) {
                synchronized (Solution.class) {
                    intConsumer.accept(aa);
                    aa++;
                }
            } else {
                Thread.currentThread().sleep(10);

            }
        }
    }
}
class ChildThread extends Thread{
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        ChildThread childThread = new ChildThread();
        childThread.start();
    }
}

class ChildRunnable implements Runnable{
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        Thread thread = new Thread(new ChildRunnable());
        thread.start();
    }
}

class MyCallable implements Callable {

    public static void main(String[] args) {
        System.out.println("main start");
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        // Future<?> future = threadPool.submit(new MyRunnable()) ;
        Future<String> future = threadPool.submit(new MyCallable());
        try {
            // 这里会发生阻塞
            System.out.println(future.get());
        } catch (Exception e) {

        } finally {
            threadPool.shutdown();
        }
    }

    @Override
    public Object call() throws Exception {
        Random generator = new Random();

        Integer randomNumber = generator.nextInt(5);

        // To simulate a heavy computation,
        // we delay the thread for some random time
        Thread.sleep(randomNumber * 1000);
        return randomNumber;
    }
}