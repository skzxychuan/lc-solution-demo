package hot100.question40;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * #47 全排列2(与#46的区别是输入数据可能有重复)
 *
 * 同#46一样，这种需要列举所有可能的题就应该想到 回溯算法(画一棵树) + 剪枝
 * 只不过这里的输入数字可能有重复，根据画出来的树形图，
 * 相同的数作为树的根节点时，枚举出来的所有组合是一样的，所以跳过相同的数就可以了。
 */
public class Solution {

    public List<List<Integer>> permutation(int[] combine) {

        if (null == combine || combine.length == 0) {
            return new ArrayList<>();
        }

        // 用来存放已经遍历过的根节点，相同根节点只需要遍历一次
        Set<Integer> existRoot = new HashSet<>();

        // 用来存放当前的组合路径中数的下标（注意: 是数的下标，不是数，这是与#46不同的地方,
        // 因为可能有重复数，不知道重复的数是否加入了路径中，但是用下标就可以区分开）
        List<Integer> idxPath = new ArrayList<>();

        // 所有排列的下标列表
        List<List<Integer>> idxRes = new ArrayList<>();

        for (int idx = 0; idx < combine.length; idx++) {
            if (existRoot.contains(combine[idx])) {
                continue;
            }
            existRoot.add(combine[idx]);
            dfs(combine, idxPath, idx, idxRes);
            // 注意这里非常容易忘记，要把根节点退出来
            idxPath.remove(idxPath.size() - 1);
        }

        System.out.println(idxRes);

        // 把下标转成结果
        List<List<Integer>> res = new ArrayList<>();
        for (List<Integer> item : idxRes) {
            List<Integer> resItem = new ArrayList<>();
            for (Integer idx : item) {
                resItem.add(combine[idx]);
            }
            res.add(resItem);
        }

        System.out.println(res);

        return res;
    }

    /**
     * @param combine
     * @param idxPath
     * @param curIdx        数组下标，用于指示当前那个节点入path
     * @param idxRes
     */
    private void dfs(int[] combine, List<Integer> idxPath, int curIdx, List<List<Integer>> idxRes) {
        idxPath.add(curIdx);

        if (idxPath.size() == combine.length) {
            idxRes.add(new ArrayList<>(idxPath));
            return;
        }

        // 用来对同层兄弟节点去重
        Set<Integer> exist = new HashSet<>();
        for (int i = 0; i < combine.length; i++) {
            // 跳过已经被添加到路径中的下标
            if (idxPath.contains(i)) {
                continue;
            }
            // 这里需要注意，与#46的不同，同父节点的同层兄弟节点，也不需要遍历重复的数
            if (exist.contains(combine[i])) {
                continue;
            }
            exist.add(combine[i]);
            dfs(combine, idxPath, i, idxRes);
            idxPath.remove(idxPath.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] combine = new int[]{1, 2, 3, 1};
        new Solution().permutation(combine);
    }

}
