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
       // int[] A = {8, 3, 8, 5, 1, 5};
        int[] A = {773, 197, 9, 545, 107, 356, 155, 194, 375, 733, 705, 415, 496, 615, 187, 121, 448, 408, 168, 770, 904, 906, 903, 77, 118, 734, 335, 426, 733, 988, 999, 506, 186, 360, 403, 293, 716, 559, 839, 444, 644, 896, 211, 493, 511, 398, 614, 960, 807, 782, 82, 63, 689, 985, 140, 159, 71, 827, 586, 804, 168, 585, 662, 354, 297, 66, 999, 13, 977, 191, 457, 973, 87, 668, 466, 599, 419, 81, 911, 578, 863, 993, 641, 904, 330, 133, 416, 401, 313, 2, 558, 481, 939, 220, 835, 236, 638, 186, 601, 967, 377, 411, 941, 465, 431, 407, 416, 850, 840, 679, 428, 56, 24, 421, 960, 354, 555, 728, 755, 868, 82, 665, 349, 373, 886, 536, 609, 524, 722, 563, 844, 452, 974, 785, 269, 405, 544, 685, 608, 385, 716, 36, 441, 740, 810, 753, 94, 365, 482, 201, 585, 564, 867, 934, 938, 753, 822, 899, 629, 896, 462, 473, 348, 788, 610, 617, 546, 155, 654, 154, 540, 370, 542, 981, 110, 704, 86, 204, 69, 568, 758, 6, 485, 625, 292, 423, 730, 114, 674, 711, 11, 137, 537, 359, 277, 147, 329, 175, 654, 983, 681, 546, 706, 576, 879, 816, 280, 966, 373, 350, 534, 131, 356, 19, 108, 649, 794, 838, 115, 469};
        int k = 20;
        System.out.println(PartitioningArray(A,k));
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
     *              否： 判断不同重复数的数量是否大于大于n/n+1（有余数）
     *                  是：false
     *                  否：
     *
     *
     */
    public static boolean PartitioningArray(int[] A, int k) {
        // write your code here
        int l = A.length;

        int m = A.length/k;
        if (m==0){
            return false;
        }
        int n = A.length%k;
        if (n>0){
            return false;
        }
        int d = m;
        HashMap<Integer,Integer> map = new HashMap<>();
        HashMap<String,Boolean> map1 = new HashMap<>();
        map1.put("aa",false);


        Set<Integer> set = new HashSet<>();
        Arrays.stream(A).forEach(i->{
            Integer num=1;
            if(set.contains(i)){
                set.remove(i);
                map.put(i,num+1);
            }else
            if(map.get(i)!=null)
            {
                num=map.get(i)+1;

                map.put(i,num);
                if (num>d){
                    map1.put("aa",true);
                }

            }else {
                set.add(i);
            }
        });
        if (map1.get("aa")){
            return  false;
        }

            int ss =  set.size()+map.size();
            if(ss<=m){
                return  false;
            }else  if(ss<=k){
                return  false;

            }

        return true;
    }
}
