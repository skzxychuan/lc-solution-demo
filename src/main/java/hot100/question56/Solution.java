package hot100.question56;


import java.util.Arrays;
import java.util.Comparator;

/**
 * #56 合并区间
 */
public class Solution {

    /**
     * TODO 方法一： 自己写的错误方法(如果不加排序)： 动态规划 + 迭代 （想复杂了，时间复杂度更高）
     * TODO 不加排序就解决不了不相邻的可合并区间的问题，比如：[[2,3],[5,5],[2,2],[3,4]]
     *
     * 状态定义：状态dp[i]=合并后位置i的输出
     *
     * 状态转移方程，以[[1,3],[2,6],[8,10],[9,18]]为例：
     * i=0      [1,3]           不可合并取原值 [1,3]
     * i=1      [1,3],[2,6]     可合并 [1,6], 同时将dp[0]清空dp[0]=[0,0](设置为特殊值[0,0])
     * i=2      [1,6][8,10]     不可合并取原值 [8,10]
     * i=3      [8,10][9,18]    可合并 [8,18], 同时将dp[2]清空dp[2]=[0,0]
     * i=...
     * 所以状态转移方程：
     * 1.当不可合并时，dp[i]=intervals[i];
     * 2.当可合并时，dp[i]=merge(intervals[i], dp[i-1]) && dp[i-1]=[0,0]
     *
     * 初始化：dp[0]=intervals[0]
     *
     * 输出：遍历dp[]列表，所有不为[0,0]的就是输出
     *
     * @param intervals
     * @return
     */
    public int[][] merge2(int[][] intervals) {

        // 迭代前的长度
        int pre = intervals.length;

        // TODO 一开始没有加这里的排序，导致无法解决不相邻区间可合并的情况，比如：[[2,3],[5,5],[2,2],[3,4]]
        // TODO 加了排序之后，其实就保证了可合并的区间一定是相邻的，具体的证明见官方题解
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        // 动态规划合并
        int[][] res = mergeByDp(intervals);

        // 迭代，迭代后的长度不等于迭代前的长度,继续迭代，直到长度不变，说明没有可合并的了
        while (res.length != pre) {
            pre = res.length;
            res = mergeByDp(res);
        }

        return res;
    }

    private int[][] mergeByDp(int[][] intervals) {
        // 状态表
        int[][] dp = new int[intervals.length][2];

        // 初始化
        dp[0] = intervals[0];

        // 记录合并次数
        int mergeCnt = 0;
        for (int i = 1; i < intervals.length; i++) {
            if (canMerge(dp[i - 1], intervals[i])) {
                dp[i] = getMerge(dp[i - 1], intervals[i]);
                dp[i - 1] = new int[]{0, 0};
                mergeCnt++;
            } else {
                dp[i] = intervals[i];
            }
        }

        int i = 0;
        // 每合并一次长度就减一
        int[][] res = new int[intervals.length - mergeCnt][2];
        for (int[] item : dp) {
            if (item[0] != 0 || item[1] != 0) {
                res[i++] = item;
            }
        }
        return res;
    }

    private boolean canMerge(int[] a, int[] b) {
        int xMax = Math.max(a[0], b[0]);
        int yMin = Math.min(a[1], b[1]);
        if (xMax <= yMin) {
            return true;
        }
        return false;
    }

    private int[] getMerge(int[] a, int[] b) {
        return new int[]{Math.min(a[0], b[0]), Math.max(a[1], b[1])};
    }

    /** 方法二： 标准题解：排序
     * @param args
     */


    public static void main(String[] args) {
        int[][] intervals = new int[][]{{1,4},{0,4}};
        new Solution().merge2(intervals);
    }
}
