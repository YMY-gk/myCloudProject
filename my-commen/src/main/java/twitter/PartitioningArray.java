package twitter;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个数字数组，您需要检查是否可以将该数组划分为每个长度为k的子序列，例如：
 *
 * 数组中的每个元素仅在一个子序列中出现
 * 子序列中的所有数字都是不同的
 * 数组中具有相同值的元素必须位于不同的子序列中
 * 是否可以对满足以上条件的数组进行分区？ 如果可能，返回true，否则返回false。
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
 * 解释: 没有办法将数组划分为多个子序列，使所有子序列的长度均为3，并且数组中的每个元素都恰好在一个子序列中出现，因此答案为假。
 * 标签
 */
public class PartitioningArray {
    public static void main(String[] args) {
        int[] sales=new int[]{1,2,3,4};
        System.out.println(partitioningArray(sales,3));

    }

    /**
     * @param A: Integer array
     * @param k: a integer
     * @return: return is possible to partition the array satisfying the above conditions
     */
    public static boolean partitioningArray(int[] A, int k) {
        // write your code here
        int l = A.length;

        int m = A.length%k;
        if(m!=0){
            return  false;
        }
        Set<Integer> set = new HashSet<>();
        Arrays.stream(A).forEach(i->{
            set.add(i);
        });
        int sl = set.size();
        if (sl!=l){
            return  false;
        }
        return true;
    }
}
