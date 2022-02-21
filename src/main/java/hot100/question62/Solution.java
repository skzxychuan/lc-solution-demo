package hot100.question62;

import java.util.ArrayList;
import java.util.List;

/**
 * #62 不同路径
 * 最好再对比一下 #64最小路径和 这道题
 */
public class Solution {

    /**
     * 回溯 + 剪枝
     * 典型的回溯算法，画出树形图就可以发现：每一步都可以选择向下或者向右,直到能向下和向右的次数消耗完。
     * TODO 这个题虽然对了 但是提交到页面上通过不了，提示"超出输出限制"
     * TODO 根据提示"题目数据保证答案小于等于 2 * 10^9",
     * TODO 问题应该出在保存了路径(内存消耗过大)，实际上只需要计数，不需要路径
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {

        if (m == 1 && n == 1) {
            return 1;
        }

        // 用来保存所有路径
        List<List<Integer>> pathList = new ArrayList<>();

        // 记录当前路径：0向下 1向右
        List<Integer> path = new ArrayList<>();

        dfs(m, n, 0, 0, path, pathList);

        return pathList.size();
    }

    private void dfs(int m, int n, int downCnt, int rightCnt, List<Integer> path, List<List<Integer>> pathList) {

        // 当向下或者向右的次数都消耗完时，就是一条到终点的路径
        if (downCnt == m - 1 && rightCnt == n - 1) {
            pathList.add(new ArrayList<>(path));
            System.out.println(path);
            return;
        }

        // 还可以向下则继续向下
        if (downCnt < m) {
            path.add(0);
            dfs(m, n, downCnt + 1, rightCnt, path, pathList);
            path.remove(path.size() - 1);
        }


        // 还可以向右则继续向右
        if (rightCnt < n) {
            path.add(1);
            dfs(m, n, downCnt, rightCnt + 1, path, pathList);
            path.remove(path.size() - 1);
        }
    }

    /**
     * 回溯 + 剪枝 (不记录路径)
     * TODO 解法对了，并且也没记录路径了，提交到页面还是超出时间限制
     * 典型的回溯算法，画出树形图就可以发现：每一步都可以选择向下或者向右,直到能向下和向右的次数消耗完。
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths2(int m, int n) {

        if (m == 1 && n == 1) {
            return 1;
        }

        int[] pathSum = new int[]{0};


        dfs2(m, n, 0, 0, pathSum);

        return pathSum[0];
    }

    private void dfs2(int m, int n, int downCnt, int rightCnt, int[] pathSum) {

        // 当向下或者向右的次数都消耗完时，就是一条到终点的路径
        if (downCnt == m - 1 && rightCnt == n - 1) {
            pathSum[0] = pathSum[0] + 1;
            return;
        }

        // 还可以向下则继续向下
        if (downCnt < m) {
            dfs2(m, n, downCnt + 1, rightCnt, pathSum);
        }

        // 还可以向右则继续向右
        if (rightCnt < n) {
            dfs2(m, n, downCnt, rightCnt + 1, pathSum);
        }
    }

    /**
     * 精选题解一： 排列组合（这里不写）
     * 精选题解二： 动态规划
     *
     * 定义状态： dp[i,j]为到达[i,j]点的路径总和
     * 而对于任意一点[i,j], 那要么从[i-1,j]过来，要么从[i,j-1]过来，所以：
     * 状态转移方程： dp[i,j]=dp[i-1,j]+dp[i,j-1]
     * 初始化: 1.对于[0,0]这个点，就只有原地不动一种情况，所以dp[0,0]=1
     *         2.对于边界上的点，只能往一个方向走，所以dp[0,j]=dp[i,0]=1
     * 输出：dp[m-1,n-1]
     */
    public int uniquePaths3(int m, int n) {

        // 定义状态表，动态规划就是填表
        int[][] dp = new int[m][n];

        // 初始化
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }


    public static void main(String[] args) {
        System.out.println(new Solution().uniquePaths2(3, 7));
    }
}
