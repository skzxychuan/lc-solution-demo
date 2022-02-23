package hot100.question78;

import java.util.ArrayList;
import java.util.List;

/**
 * #78 子集
 */
public class Solution {
    /**
     * 回溯算法
     *
     * 回溯与DFS的区别：
     *     1. DFS 是一个劲的往某一个方向搜索，而回溯算法建立在 DFS 基础之上的，但不同的是在搜索过程中，达到结束条件后，恢复状态，
     *     回溯上一层，再次搜索。因此回溯算法与 DFS 的区别就是有无状态重置。
     *     2.何时使用回溯算法
     *     当问题需要 "回头"，以此来查找出所有的解的时候，使用回溯算法。即满足结束条件或者发现不是正确路径的时候(走不通)，
     *     要撤销选择，回退到上一个状态，继续尝试，直到找出所有解为止。
     *     3.怎么样写回溯算法(从上而下，※代表难点，根据题目而变化)
     *         ①画出递归树，找到状态变量(回溯函数的参数)，这一步非常重要※
     *         ②根据题意，确立结束条件
     *         ③找准选择列表(与函数参数相关),与第一步紧密关联※
     *         ④判断是否需要剪枝
     *         ⑤作出选择，递归调用，进入下一层
     *         ⑥撤销选择
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {

        // 路径，即当前集合
        List<Integer> path = new ArrayList<>();

        // 结果
        List<List<Integer>> res = new ArrayList<>();

        dfs(nums, path, 0, res);


        return res;
    }

    // 状态变量就是idx
    private void dfs(int[] nums, List<Integer> path, int idx, List<List<Integer>> res) {
        res.add(new ArrayList<>(path));
        // 选择列表
        for (int i = idx; i < nums.length; i++) {
            // 做出选择
            path.add(nums[i]);
            // 递归调用，进入下一层
            dfs(nums, path, i + 1, res);
            // 撤销选择
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3};
        new Solution().subsets(nums);
    }
}
