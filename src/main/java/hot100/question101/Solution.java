package hot100.question101;

import java.util.LinkedList;
import java.util.Queue;

/**
 * #101 对称二叉树
 */
public class Solution {
    /**
     * 方法一、递归
     * 镜像条件：1.当前对应节点的值相等 2.当前对应节点的子树依然镜像
     * (左边的左子树等于右边的对应右子树，左边的右子树等于右边对应的左子树)
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return false;
        }

        return recursion(root.left, root.right);
    }

    private boolean recursion(TreeNode root1, TreeNode root2) {
        if (null == root1 && null == root2) {
            return true;
        }
        if (null == root1 || null == root2) {
            return false;
        }
        return root1.val == root2.val && recursion(root1.left, root2.right) && recursion(root1.right, root2.left);
    }

    /**
     * 方法二、迭代
     * 同时层序遍历两边，镜像对称的相邻入队列。
     */
    public boolean isSymmetric2(TreeNode root) {
        if (root == null) {
            return false;
        }

        return iteration(root.left, root.right);
    }

    private boolean iteration(TreeNode root1, TreeNode root2) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root1);
        queue.offer(root2);

        while(!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();
            if (null == node1 && null == node2) {
                continue;
            }
            if (null == node1 || null == node2 || node1.val != node2.val) {
                return false;
            }
            queue.offer(node1.left);
            queue.offer(node2.right);
            queue.offer(node1.right);
            queue.offer(node2.left);
        }
        return true;
    }

}
