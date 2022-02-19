package hot100.question46;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * #46 全排列
 * 像这种要找出所有排列/组合的题，应该自然想到回溯算法，同理的还有#39求组合总和
 * 而回溯算法都是先把枚举的树画出来，然后找规律，再编码
 */
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        if (1 == nums.length) {
            res.add(Arrays.asList(Integer.valueOf(nums[0])));
            return res;
        }

        // 保存深度优先遍历的路径
        List<Integer> path = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            dfs(nums, path, i, res);
            // TODO 注意 这一个remove很容易被忽略，导致结果不对
            path.remove(path.size() - 1);
        }

        return res;
    }

    private void dfs(int[] nums, List<Integer> path, int curIdx, List<List<Integer>> res) {

        path.add(nums[curIdx]);
        System.out.println(path);

        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (path.contains(nums[i])) {
                continue;
            }
            dfs(nums, path, i, res);
            // 从画出来的树形图可以看出来，每结束一次递归(树的子节点到父节点)，要移除刚添加的数字
            path.remove(path.size() - 1);
            System.out.println(path);
        }
    }

    public static void main(String args[]) {
        int[] nums = {1 ,2 ,3};
        Solution solution = new Solution();
        solution.permute(nums);
    }
}
