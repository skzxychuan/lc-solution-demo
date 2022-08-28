package hot100.question221;

/**
 * #221 最大正方形、
 * 这题基本想不到，看答案没啥可耻的
 */
public class Solution {

    /**
     * 最高赞题解：动态规划
     * 此题求最大正方形面积，面积=边长*边长，所以转为找最大边长的正方形。
     *
     * 定义状态：dp[i + 1][j + 1] 表示 「以第 i 行、第 j 列为右下角的正方形的最大边长」，则有(推到过程见题解)：
     * TODO 推导过程中，那个新增一行一列的操作很巧妙
     *
     * 状态转移方程：dp[i + 1][j + 1] = min(dp[i][j + 1]), dp[i + 1][j], dp[i][j]) + 1
     *
     * 初始化：新增的虚拟的一行(上)一列(左)由于都是'0'，不能构成正方形，所以这些格子的dp=0
     *
     * 输出：动态规划就是在填表，填表的过程中记录最大边长maxEdgeLen，最后面积就是 maxEdgeLen * maxEdgeLen
     *
     * 空间优化：略
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {

        int row = matrix.length;
        int col = matrix[0].length;

        // 行列+1的原因是：假设新增了一行(上)一列(左)'0'来统一后续操作，防止大量的边界处理（类似列表的哨兵作用）
        // 而且新增的一行一列'0'的格子的dp状态都是0(按题意只有'1'才能构成正方形)，从而满足了动态规划的“无后效性”
        // int[]数组默认就是初始化0，没必要再显示初始化
        int[][] dp = new int[row + 1][col + 1];

        int maxEdgeLen = 0;
        for (int r = 1; r < row + 1; r++) {
            for (int c = 1; c < col + 1; c++) {
                // 如果是'1'，则有可能构成正方形，此时求最大边长
                // TODO 上面的r和c都是假设新增一行一列后的偏移量，而matrix实际并没有新增，需要减去1
                if (matrix[r - 1][c - 1] == '1') {
                    int up = dp[r - 1][c];
                    int left = dp[r][c - 1];
                    int leftUp = dp[r - 1][c - 1];
                    dp[r][c] = Math.min(Math.min(up, left), leftUp) + 1;
                    if (dp[r][c] > maxEdgeLen) {
                        maxEdgeLen = dp[r][c];
                    }
                }
            }
        }
        return maxEdgeLen * maxEdgeLen;
    }
}
