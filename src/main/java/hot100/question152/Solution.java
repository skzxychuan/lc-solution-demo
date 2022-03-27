package hot100.question152;

/**
 * #152 乘积最大子数组
 */
public class Solution {

    /**
     * 官解：动态规划
     *
     * 定义状态：dp[i] = 以下标i结尾的数组的最大子数组乘积
     * 状态转移方程：可以想到dp[i] = Max(dp[i-1] * nums[i], nums[i])
     * TODO 但是这个状态转移方程只对了一半，碰到负数的时候就会出错，
     * TODO 这里需要特殊处理负数，具体见官解，比精选解好理解
     * TODO 总之就是，最大值不一定来自上一个最大值，当当前值是负数的时候，
     * TODO 最大值还可能来自上一个最小值，这样负负得正，反而最大
     * TODO 所以还需要维护一个最小值
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {

        if (nums.length == 1) {
            return nums[0];
        }

        // 第一个状态列表，维护以i结尾的数组的最大子数组乘积
        int[] maxDp = new int[nums.length];
        // 第二个状态列表，维护以i结尾的数组的最小子数组乘积
        int[] minDp = new int[nums.length];

        // 初始化：
        maxDp[0] = nums[0];
        minDp[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // minDp[i - 1] * nums[i]表示当nums[i]是负数的时候，当前最大值可能来自上一个最小值
            maxDp[i] = Math.max(maxDp[i - 1] * nums[i], Math.max(minDp[i - 1] * nums[i], nums[i]));
            // 同理维护最小值, maxDp[i - 1] * nums[i]表示当前最小可能来自上一个的最大
            minDp[i] = Math.min(minDp[i - 1] * nums[i], Math.min(maxDp[i - 1] * nums[i], nums[i]));
        }

        int max = maxDp[0];
        for (int i = 1; i < maxDp.length; i++) {
            max = maxDp[i] > max ? maxDp[i] : max;
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-2,3,-4};
        Solution solution = new Solution();
        solution.maxProduct(nums);
    }
}
