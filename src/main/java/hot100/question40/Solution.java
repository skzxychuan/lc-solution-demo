package hot100.question40;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * #40 组合总和2
 * 回溯 + 剪枝
 * 和#39类似，但数组中的元素不能重复使用
 * 好好体会和#39的区别, 建议做题顺序 #46 -> #47 -> #39 -> #40，最后再做本题感觉会好些
 */
public class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        List<List<Integer>> resList = new ArrayList<>();

        // 先排序，对于比target还大的元素，直接剪枝
        Arrays.sort(candidates);

        // path用来保存候选数字的下标，注意是下标
        List<Integer> path = new ArrayList<>();

        Set<Integer> existNums = new HashSet<>();
        for (int i = 0; i < candidates.length; i++) {
            // 剪枝，target不可能由比它大的数加出来(题目输入都是正数)
            if (candidates[i] > target) {
                break;
            }
            // 剪枝，相同的数只需要减一次，否则会有重复
            if (existNums.contains(candidates[i])) {
                continue;
            }
            existNums.add(candidates[i]);
            dfs(candidates, i, target, path, resList);
            path.remove(path.size() - 1);
        }

        // 把下标转成值
        for (List<Integer> idxList : resList) {
            for (int i = 0; i < idxList.size(); i++) {
                idxList.set(i, candidates[idxList.get(i)]);
            }
        }

        return resList;
    }

    private void dfs(int[] candidates, int idx, int target, List<Integer> path, List<List<Integer>> resList) {
        int newTarget = target - candidates[idx];
        path.add(idx);

        if (newTarget == 0) {
            resList.add(new ArrayList<>(path));
            return;
        }
        if (newTarget < 0) {
            return;
        }

        Set<Integer> existNums = new HashSet<>();
        // 注意这里是i=idx而不是i=0, 这是和#39最大的区别
        for (int i = idx; i < candidates.length; i++) {
            // 剪枝，target不可能由比它大的数加出来(题目输入都是正数)
            if (candidates[i] > newTarget) {
                break;
            }
            // 剪枝,用过的数不能再用
            if (path.contains(i)) {
                continue;
            }
            // 剪枝，相同的数只需要减一次，否则会有重复
            if (existNums.contains(candidates[i])) {
                continue;
            }
            existNums.add(candidates[i]);
            dfs(candidates, i, newTarget, path, resList);
            path.remove(path.size() - 1);
        }
    }
}
