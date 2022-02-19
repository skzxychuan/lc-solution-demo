package hot100.question39;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * #39 组合总和
 * 回溯算法 + 剪枝
 * 对于这类寻找所有可行解的题，我们都可以尝试用「搜索回溯」的方法来解决。
 * 可以画出树形图。建议自己在纸上画出树，这一类问题都需要先画出树形图，然后编码实现。
 * 最好官方题解和最高赞题解都看一下，角度稍微有一点不同
 */
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<List<Integer>> res = new ArrayList<>();
        // 一个列表用来保存当前的路径值
        List<Integer> path = new ArrayList<>();

        if (null == candidates || 0 == candidates.length) {
            return res;
        }

        // 排序candidates，很明显，target不可能用比自己大的数组合出来，所以>target的数都可以省略
        Arrays.sort(candidates);

        // 枚举的结果是一棵树，深度优先遍历这棵树
        dfs(candidates, target, 0, path, res);
        return res;
    }

    /**
     *
     * @param candidates    候选值列表
     * @param target        目标数
     * @param idx           索引下标
     * @param path          当前路径
     * @param res           结果
     */
    private void dfs(int[] candidates, int target, int idx, List<Integer> path, List<List<Integer>> res) {
        // 当前target<0的时候结束递归
        // 如果在下面判断了是否target < candidates[i]，那就就不需要判断target < 0了
        // 注意前提是对candidates排序过
//        if (target < 0) {
//            return;
//        }


        // target为0时也要结束递归，并且当前路径就是答案之一
        if (0 == target) {
            // 这里要注意新new一个列表
            res.add(new ArrayList<>(path));
            return;
        }

        // 用当前target去尝试减去候选值中的每一个数
        for (int i = idx; i < candidates.length; i++) {
            // 比当前target还大的数就不需要去尝试了
            if (target < candidates[i]) {
                break;
            }

            // 使用了candidates[i]，记录到路径中
            path.add(candidates[i]);
            // 将target-candidates[i]作为新的target递归，由于可以重复使用，下标还是i
            dfs(candidates, target - candidates[i], i, path, res);
            // TODO 这里很用容易漏掉，其实只要看看画出来的树形图，退出一层dfs()之后（也就是树中从子节点回到父节点），需要移除刚增加进路径的元素
            path.remove(path.size() - 1);
        }
    }
}
