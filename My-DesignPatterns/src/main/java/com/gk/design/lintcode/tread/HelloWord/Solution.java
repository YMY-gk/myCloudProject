package com.gk.design.lintcode.tread.HelloWord;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/7/28 15:34
 */
public class Solution {
    public void printNumberInMainSubThread(int n, IntConsumer intConsumer) throws InterruptedException {
        // write your code here
        Runnable runnable = ()->{

        };
        new Thread(runnable).start();
        Thread.currentThread().sleep(100);
    }

    public void printHelloWorld(IntConsumer print) throws InterruptedException {
        Runnable runnable = ()->{
            print.accept(1);
        };
        new Thread(runnable).start();
        Thread.currentThread().sleep(1000);
    }

    public void sleepSort(double[] nums) throws Exception {
        Thread[] threads = new Thread[nums.length];
        int i=0;
        for(double x:nums){
            Runnable runnable = ()->{
                try {
                    Thread.currentThread().sleep((long) (x*100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               // Main.printNumber(x);
            };
            threads[i]=new Thread(runnable);
            i++;
        }
        for(int j = 0; j < threads.length; j++) {
            threads[j].start();
        }
        for(int j = 0; j < threads.length; j++) {
            threads[j].join();
        }
    }
}