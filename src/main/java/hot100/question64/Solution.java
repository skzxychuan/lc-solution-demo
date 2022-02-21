package hot100.question64;

/**
 * #64最小路径和
 * 最好再对比一下 #62不同路径 这道题
 */
public class Solution {

    /**
     * 动态规划
     * 定义状态： dp[i,j]为点[i,j]的最小路径和
     * 状态方程：
     * 1.对任意一个非边界点[i,j](i>0,j>0)，只可能从上方[i-1,j]或者左方[i,j-1]过来,所以哪个方向的路径和最小就选哪个，所以：
     *  dp[i,j] = min(dp[i-1,j],dp[i,j-1]) + grid[i,j],  其中i>0,j>0
     * 2.如果i在边界，即i=0，此时只能从左边过来，那么dp[0,j]=dp[0,j-1]+grid[0][j]
     * 3.如果j在边界，即j=0, 此时只能从上边过来，那么dp[i,0]=dp[i-1,0]+grid[i][0]
     * 4.如果i,j都在边界，即原点，此时dp[0,0]=grid[0,0]
     * 初始化：dp[0,0]=grid[0,0]
     * 输出：dp[m-1][n-1]
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        // 定义状态表，动态规划就是填表
        int[][] dp = new int[m][n];

        // 初始化
        dp[0][0] = grid[0][0];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (i == 0) {
                    dp[i][j] = dp[i][j-1] + grid[0][j];
                } else if (j == 0) {
                    dp[i][j] = dp[i-1][j] + grid[i][0];
                } else {
                    dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
                }
            }
        }

        return dp[m - 1][n - 1];
    }
}
