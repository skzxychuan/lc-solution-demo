package hot100.question136;

/**
 * #136 只出现一次的数字
 */
public class Solution {

    /**
     * 方法一、异或运算
     * 题目要求时间复杂度为O(n)，且空间复杂度为O(1)
     * 那些容易想到的解法都不满足题意，这里要巧妙的利用异或运算的性质
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int a = 0;
        for (int num : nums) {
            a = a ^ num;
        }
        return a;
    }
}
