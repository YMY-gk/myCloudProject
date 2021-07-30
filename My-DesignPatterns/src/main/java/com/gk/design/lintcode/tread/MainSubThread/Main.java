package com.gk.design.lintcode.tread.MainSubThread;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.function.IntConsumer;

public class Main {

    public static void main(String[] args) {
        try {
            int n=50;
            IntConsumer intConsumer = (x->{
                try {
                    int num = x % 30;
                    String name = Thread.currentThread().getName();
                    if ((num >= 11 && num <= 30 || num == 0) && "main".equals(name)){
                        throw new Exception("You should call this method in a sub thread when you passed x=" + x);
                    }
                    if (num >= 1 && num <= 10 && !"main".equals(name)){
                        throw new Exception("You should call this method in main thread when you passed x=" + x);
                    }
                    System.out.println(name+"---"+x);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Solution solution = new Solution();
            solution.printNumberInMainSubThread(n,intConsumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}