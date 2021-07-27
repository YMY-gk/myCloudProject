package com.gk.design.lintcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/7/27 17:16
 *
 *
 * 给定一个数字数组，您需要检查是否可以将该数组划分为每个长度为k的子序列，例如：
 *
 * 数组中的每个元素仅在一个子序列中出现
 * 子序列中的所有数字都是不同的
 * 数组中具有相同值的元素必须位于不同的子序列中
 * 是否可以对满足以上条件的数组进行分区？ 如果可能，返回true，否则返回false。
 *
 * 样例
 * 示例1:
 * 输入:
 * A:[1, 2, 3, 4]
 * k = 2
 * 输出: true
 * 解释:
 * 那么一种可能的方法是选择数组{1，2}的前2个元素作为第一个子序列，接下来的2个元素{3，4}作为下一个子序列。所以答案是正确的
 * 示例2:
 * 输入:
 * A: [1, 2, 2, 3]
 * k: 3
 * 输出: false
 * 解释: 没有办法将数组划分为多个子序列，使所有子序列的长度均为3，并且数组中的每个
 */
public class Solution {
    public static void main(String[] args) {

    }

    /**
     *   解题思路：
     *   获取所有没有重复的数据
     *   获取每个重复数据和其存在的个数 map
     *
     *   1. 判断除数n是否大于0
     *       小于：false
     *       大于:
     *          判断段是否有重复，有且相同重复数量是否大于n/n+1（有余数）
     *              是：false
     *              否：true
     *
     */
    public boolean PartitioningArray(int[] A, int k) {
        // write your code here
        int l = A.length;

        int m = A.length/k;
        if (m==0){
            return false;
        }
        int n = A.length%k;
        if (n>0){
            m=m+1;
        }
        int d = m;
        HashMap<Integer,Integer> map = new HashMap<>();
        AtomicBoolean aa = new AtomicBoolean(false);

        Set<Integer> set = new HashSet<>();
        Arrays.stream(A).forEach(i->{
            Integer num=1;
            if(set.contains(i)){
                set.remove(i);
                map.put(i,num);
            }else
            if(map.get(i)!=null)
            {
                num=map.get(i)+1;

                map.put(i,num);
                if (num>d){
                    aa.set(true);
                }

            }else {
                set.add(i);
            }
        });
        if (aa.get()){
            return  false;
        }
        if (map.size()>d){
            return  false;
        }

        return true;
    }
}
