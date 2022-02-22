package hot100.question70;

/**
 * #70 爬楼梯
 */
public class Solution {

    /**
     * 动态规划(自己想的)
     *
     * 定义状态：对于任意一个台阶i，到达这个台阶只有两种方式：1.从i-1个台阶走1步 2.从i-2个台阶走2步(从第3个台阶开始)，
     * 因此，定义状态dp[i]为到第i个台阶的走法的数量。
     *
     * 状态转移方程：dp[i] = dp[i - 1] + dp[i - 2], i >= 2
     *
     * 初始化： dp[0]=1, dp[1]=2 (到第二个台阶只有两种方式：走两个1步或者走一个2步)
     *
     * 输出：dp[i-1]
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {

        if (1 == n) {
            return 1;
        }

        if (2 == n) {
            return 2;
        }

        // 状态列表，动态规划就是在填表
        int[] dp = new int[n];

        // 初始化
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i< n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n - 1];
    }
}
