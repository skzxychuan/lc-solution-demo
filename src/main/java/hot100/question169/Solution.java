package hot100.question169;

/**
 * #169 多数元素
 * 尝试设计时间复杂度为 O(n)、空间复杂度为 O(1) 的算法解决此问题。
 * TODO 这个题有5种解法，具体见官解，但是满足进阶要求的只有 随机法和摩尔投票法，这里只写摩尔投票
 */
public class Solution {

    /**
     * 官解：摩尔投票
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        int condidate = 0;
        int count = 0;
        for (int num : nums) {
            if (count == 0) {
                condidate = num;
            }
            count += num == condidate ? 1 : -1;
        }
        return condidate;
    }
}
