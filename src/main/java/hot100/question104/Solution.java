package hot100.question104;

import java.util.LinkedList;
import java.util.Queue;

/**
 * #104 二叉树的最大深度
 */
public class Solution {

    /**
     * 自解1：层序遍历记录层数（也是广度优先搜索）
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (null == root) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int levelNum = 0;
        while (!queue.isEmpty()) {
            // 记下当前层队列大小
            int levelNodeNum = queue.size();
            // 一次性一层出队列或者入队列，记录层数
            for (int i = 0; i < levelNodeNum; i++) {
                TreeNode node = queue.poll();
                if (null == node) {
                    continue;
                }
                // 这里要判空，否则最后一个节点的左右null孩子会导致计算的层数多一层
                if (null != node.left) {
                    queue.offer(node.left);
                }
                if (null != node.right) {
                    queue.offer(node.right);
                }
            }
            levelNum++;
        }

        return levelNum;
    }

    /**
     * 方法二、深度优先（递归）
     * 如果我们知道了左子树和右子树的最大深度 l 和 r，那么该二叉树的最大深度即为 max(l,r)+1
     * 而左子树和右子树的最大深度又可以以同样的方式进行计算。因此我们可以用「深度优先搜索」的方法来计算二叉树的最大深度。
     */
    public int maxDepth2(TreeNode root) {
        if (null == root) {
            return 0;
        }

        int maxLeft = maxDepth2(root.left);
        int maxRight = maxDepth2(root.right);

        return Math.max(maxLeft, maxRight) + 1;
    }
}
