package com.gk.design.lintcode.tread.HelloWord;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.IntConsumer;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/7/28 15:34
 */
public class Main {
    public static void main(String[] args) {
        try {
            String outputPath = args[1];
            PrintStream ps = new PrintStream(outputPath);
            IntConsumer print = (x -> {
                try {
                    String name = Thread.currentThread().getName();
                    if ("main".equals(name)) {
                        throw new Exception("You need to start a new thread.");
                    }
                    ps.write("hello world".getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Solution solution = new Solution();
            solution.printHelloWorld(print);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}