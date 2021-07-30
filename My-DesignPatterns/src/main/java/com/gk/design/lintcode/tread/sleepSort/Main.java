package com.gk.design.lintcode.tread.sleepSort;

import java.io.*;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/7/28 15:34
 */
public class Main {
    static String mainThreadName;

    public static void printNumber(double x) throws Exception{
        if(mainThreadName == Thread.currentThread().getName()) {
            Exception exception = new Exception();
            throw exception;
        }else {
           System.out.println(String.valueOf(x));
        }
    }

    public static void main(String[] args){
        try {
            mainThreadName = Thread.currentThread().getName();
            double[] array ={0.17, 0.02, 0.1};
            Solution sol = new Solution();
            sol.sleepSort(array);
            System.out.println("-----------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}