package hot100.question53;

/**
 * #53 最大子数组和
 */
public class Solution {

    /**
     * 方法一：暴力解(滑动窗口)
     * TODO 这个解在页面上提交不会通过，时间复杂度O(n^3)，超出时间限制
     * @param nums
     * @return
     */
    public int maxSubArray_1(int[] nums) {

        Integer maxSum = Integer.MIN_VALUE;

        // 窗口的长度从1到nums.length
        for (int winLen = 1; winLen <= nums.length; winLen++) {
            // 当前窗口起点下标
            for (int winStart = 0; winStart <= nums.length - winLen; winStart++) {
                // 当前窗口终点下标
                int winEnd = winStart + winLen - 1;
                // 当前窗口和
                int winSum = 0;
                for (int i = winStart; i <= winEnd; i++) {
                    winSum+=nums[i];
                }
                if (winSum > maxSum) {
                    maxSum = winSum;
                }
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(new Solution().maxSubArray_1(nums));
    }
}
