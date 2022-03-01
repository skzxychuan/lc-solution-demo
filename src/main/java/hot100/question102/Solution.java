package hot100.question102;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * #102 二叉树的层序遍历
 */
public class Solution {

    /**
     * 自解：层序遍历需要一个队列做辅助，一次把一层都取出来,同时一层都入队列
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (null == root) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            //一次把一层都取出来,同时一层都入队列
            int curLevelNum = queue.size();
            List<Integer> curLevel = new ArrayList<>();
            for (int i = 0; i < curLevelNum; i++) {
                TreeNode node = queue.poll();
                if (null == node) {
                    continue;
                }
                curLevel.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            }
            if (curLevel.size() > 0) {
                res.add(curLevel);
            }
        }
        return res;
    }
}
