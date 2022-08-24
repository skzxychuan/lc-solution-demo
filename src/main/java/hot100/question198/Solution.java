package hot100.question198;

/**
 * #198 打家劫舍
 */
public class Solution {

    /**
     * 官解：动态规划
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return nums[0];
        }
        if (len == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int prePre = nums[0];
        int pre = Math.max(nums[0], nums[1]);
        int cur = 0;

        for (int i = 2; i < len; i++) {
            cur = Math.max(prePre + nums[i], pre);
            prePre = pre;
            pre = cur;
        }

        return cur;
    }
}
