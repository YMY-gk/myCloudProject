package twitter;

/**
 * 描述
 * 给出一个数组，你需要找到在这个数组中的“平衡数”。
 * 一个“平衡数”满足 在它左边的所有数字的和 等于 在它右边的所有数字的和。
 * 你的代码应该返回平衡数的下标，如果平衡数存在多个，返回最小的下标。
 * 例如，给定数组sales = [1,2,3,4,6]，我们看到1 + 2 + 3 = 6，使用基于零的索引，sales [3] = 4是寻求的值。索引是3。
 *
 * 3 <= n <= 10^5
 * 1 <= sales[i] <= 2*10^4,where 0 <= i<n
 * 保证存在一个可行解
 * 样例
 * 示例:
 * 输入 : [1, 2, 3, 4, 6]
 * 输出 : 3
 */
public class Equilibrium {

    public static void main(String[] args) {
        //20, 24, 29, 32, 33, 34, 50, 51, 78, 97
        int[] sales=new int[]{92, 66, 26, 74, 10, 82, 40, 95, 89, 90, 13, 97, 93, 11, 22, 3, 21, 99, 60, 5, 37, 66, 22, 86, 25, 3, 40, 32, 28, 16, 91, 19, 33, 68, 92, 42, 49, 83, 36, 89, 24, 100, 86, 16, 10, 59, 19, 31, 58, 30, 87, 46, 95, 8, 31, 19, 62, 70, 50, 41, 37, 40, 59, 69, 7, 3, 10, 8, 37, 97, 96, 61, 48, 81, 76, 57, 92, 46, 39, 49, 75, 25, 46, 21, 84, 76, 40, 46, 97, 89, 86, 3917, 338, 126, 92, 39, 51, 29, 4, 2};
        //int[] sales=new int[]{1,5,2, 3, 4, 6};//01234
        System.out.println(BalancedSalesArray(sales));
    }
    /**
     * @param sales: a integer array
     * @return: return a Integer
     */
    public static int BalancedSalesArray(int[] sales) {
        int temp = 0;
        int t =-1;
        for(int i = -1,j=sales.length;i<sales.length;){
            if (temp<=0){
                j--;
                temp=sales[j]+temp;
            }
            if(j-i==1&&temp>=0){    //处理
                t=j;
                break;
            }else if(j-i==1&&temp<0){
                t=i;
                break;
            }
            if (temp>0){
                i++;
                temp = temp-sales[i];
            }
            if(j-i==1&&temp>=0){    //处理
                t=j;
                break;
            }else if(j-i==1&&temp<0){
                t=i;
                break;
            }
        }
        return t;
    }
}