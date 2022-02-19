package hot100.question53_2;

/**
 * #53 最大子数组和
 * 动态规划 / 分治
 */
public class Solution {

    /**
     * 暴力解2, 大概参考了一下动态规划题解(最高赞)写出来的暴力解(因为理解错了)，
     * 虽然超时了，但是这个暴力解的过程对理解此题的动态规划题解有很大的帮助。
     * 因为这个暴力解只考虑了以numx[i]结尾的所有子数组的内部规律，没有考虑到与nums[i + 1]的关系，其实就只差这一步。
     * TODO O(n^2)，超出时间限制
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (1 == nums.length) {
            return nums[0];
        }

        int maxSum = Integer.MIN_VALUE;
        // 第一个循环，表示以numx[i]结尾的所有子数组
        for (int i = 0; i < nums.length; i++) {
            // 表示以nums[i]结尾的所有子数组的最大子数组和
            int maxSumTmp = Integer.MIN_VALUE;
            int curSum = 0;
            // 以nums[i]结尾的所有子数组，长度范围为[1, i + 1]
            for (int len = 1; len <= i + 1; len++) {
                curSum += nums[i - len + 1];
                if (len == 1) {
                    maxSumTmp = curSum;
                } else if (nums[i - len + 1] >= 0 && curSum > maxSumTmp) {
                    // 新增的数numx[i - len + 1] >=0， 就要比较当前和与maxSumTmp的大小
                    maxSumTmp = curSum;
                }
            }
            if (maxSumTmp > maxSum) {
                maxSum = maxSumTmp;
            }
        }
        return maxSum;
    }

    /**
     * 动态规划，具体思路见题解(最高赞)。
     * 下面是根据自己理解写出来的动态规划。
     * 在草稿上画一画以nums[i]结尾的子数组和以nums[i+1]结尾的子数组，
     * 看看它们之间的规律，会好理解很多。
     * @param nums
     * @return
     */
    public int maxSubArray2(int[] nums) {
        if (1 == nums.length) {
            return nums[0];
        }

        // 状态：以i结尾的子数组的最大和
        int[] dp = new int[nums.length];

        // 初始状态
        dp[0] = nums[0];

        int maxSum = dp[0];
        for (int i = 1; i < nums.length; i++) {
            if (dp[i - 1] < 0) {
                dp[i] = nums[i];
            } else {
                dp[i] = dp[i - 1] + nums[i];
            }

            if (dp[i] > maxSum) {
                maxSum = dp[i];
            }
        }

        return maxSum;
    }

    /**
     * 分治法，结合官方题解+ 最高赞题解更容易理解
     * 本质上是把所有子数组的情况分类，所有子数组可以分为下面三大类：
     * 第 1 部分：子区间 [left, mid]；
     * 第 2 部分：子区间 [mid + 1, right]；
     * 第 3 部分：包含子区间 [mid , mid + 1] 的子区间，即 nums[mid] 与 nums[mid + 1] 一定会被选取。
     * @param nums
     * @return
     */
    public int maxSubArray3(int[] nums) {
        if (1 == nums.length) {
            return nums[0];
        }

        return maxSubArraySum(nums, 0, nums.length - 1);
    }

    private int maxSubArraySum(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }

        int mid = (left + right) / 2;
        return max3(maxSubArraySum(nums, left, mid),
                maxSubArraySum(nums, mid + 1, right),
                crossSubArraySum(nums, left, mid, right));
    }

    private int crossSubArraySum(int[] nums, int left, int mid, int right) {
        // 由于一定过sums[mid]，那么可以从mid开始中心扩散
        // [left, right]的最大连续子数组和，一定等于 mid左子区间最大连续和 + mid右子区间最大连续和
        int halfSum = 0;
        int leftMaxSum = Integer.MIN_VALUE ;
        for (int i = mid; i >= left; i--) {
            halfSum += nums[i];
            leftMaxSum = halfSum > leftMaxSum ? halfSum : leftMaxSum;
        }
        halfSum = 0;
        int rightMaxSum = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= right; i++) {
            halfSum += nums[i];
            rightMaxSum = halfSum > rightMaxSum ? halfSum : rightMaxSum;
        }

        return leftMaxSum + rightMaxSum;
    }

    private int max3(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }
}
