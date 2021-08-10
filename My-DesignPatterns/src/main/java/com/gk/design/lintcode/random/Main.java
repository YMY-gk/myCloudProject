package com.gk.design.lintcode.random;


public class Main {
        public static void main(String[] args) {
            Solution s1 = new Solution();
            Solution s2 = new Solution();

            if (s1.random1 != s2.random1) {
                throw new RuntimeException(
                        "Make sure that s1.random1 and s2.random1 are equal");
            }

            if (s1.random2 == s2.random2) {
                throw new RuntimeException(
                        "Make sure that s2.random1 and s2.random2 are not equal");
            }

            System.out.println("Run successfully");
        }
}