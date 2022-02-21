package hot100.question55;

/**
 * #55 跳跃游戏
 */
public class Solution {

    /**
     * 方法一：贪心
     * 以0下标为例，则[0, nums[0]]都可以作为起跳点，最远的那个起跳点就是nums[0]，后续下标同理
     * 那么只需要维护一个最远起跳点，如果这个最远起跳点始终>=当前起跳点，那么就可以到达最后一个下标，否则不行。
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {

        // 初始最远起跳点就是第一个数
        int maxIdx = 0;
        for (int i = 0; i < nums.length; i++) {
            // 如果当前起跳点超过了最远起跳点，那就无法到达
            if (i > maxIdx) {
                return false;
            }
            maxIdx = Math.max(i + nums[i], maxIdx);
        }

        return true;
    }
}
