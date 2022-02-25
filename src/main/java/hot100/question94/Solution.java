package hot100.question94;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * #94 二叉树的中序遍历
 * 1,2,4三种方法都需要掌握，只会递归是不行的，太简单
 */
public class Solution {

    /**
     * 方法一、递归(最简单)，但效率不高
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {

        List<Integer> res = new ArrayList<>();
        midScan(root, res);
        return res;
    }

    private void midScan(TreeNode node, List<Integer> res) {
        if (null == node) {
            return;
        }

        midScan(node.left, res);
        res.add(node.val);
        midScan(node.right, res);
    }

    /**
     * 方法二、迭代
     * 方法一的递归函数我们也可以用迭代的方式实现，
     * 两种方式是等价的，区别在于递归的时候隐式地维护了一个栈，
     * 而我们在迭代的时候需要显式地将这个栈模拟出来，其他都相同
     *
     * 时间复杂度：O(n)，其中 nn 为二叉树节点的个数。二叉树的遍历中每个节点会被访问一次且只会被访问一次。
     * 空间复杂度：O(n)。空间复杂度取决于栈深度，而栈深度在二叉树为一条链的情况下会达到 O(n) 的级别
     */
    public List<Integer> inorderTraversal2(TreeNode root) {

        List<Integer> res = new ArrayList<>();

        // 定义一个栈模拟递归的方法栈
        Deque<TreeNode> stack = new ArrayDeque<>();

        while (null != root || !stack.isEmpty()) {
            while (null != root) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }


    /**
     * 方法三、莫里斯(morris)遍历, 有点难理解，多在脑中模拟下
     *
     * Morris 遍历算法是另一种遍历二叉树的方法，它能将非递归的中序遍历空间复杂度降为 O(1)。
     *
     * Morris 遍历算法整体步骤如下（假设当前遍历到的节点为 x）：
     * 如果 x 无左孩子，先将 x 的值加入答案数组，再访问 x 的右孩子，即 x=x.right。
     * 如果 x 有左孩子，则找到 x 左子树上最右的节点（即左子树中序遍历的最后一个节点，也是 x 在中序遍历中的前驱节点），
     * 我们记为 predecessor。根据 predecessor 的右孩子是否为空，进行如下操作。
     * 如果 predecessor 的右孩子为空，则将其右孩子指向 x，然后访问 x 的左孩子，即 x=x.left。
     * 如果 predecessor 的右孩子不为空，则此时其右孩子指向 x，说明我们已经遍历完 x 的左子树，
     * 我们将 predecessor 的右孩子置空，将 x 的值加入答案数组，然后访问 x 的右孩子，即 x=x.right。
     * 重复上述操作，直至访问完整棵树。
     */
    public List<Integer> inorderTraversal3(TreeNode root) {

        List<Integer> res = new ArrayList<>();
        TreeNode predecessor = null;

        while (null != root) {
            if (null == root.left) {
                res.add(root.val);
                root = root.right;
            } else {
                // predecessor 节点就是当前 root 节点向左走一步，然后一直向右走至无法走为止
                predecessor = root.left;
                // 注意这里的后半个条件：predecessor.right != root
                while(null != predecessor.right && predecessor.right != root) {
                    predecessor = predecessor.right;
                }

                if (null == predecessor.right) {
                    predecessor.right = root;
                    root = root.left;
                } else {
                    // 这里也可以写成
//                res.add(predecessor.right.val);
                    res.add(root.val);
                    // 这里注意要断开
                    predecessor.right = null;
                    root = root.right;
                }
            }
        }

        return res;
    }

    /**
     * 方法四、颜色标记法(最高赞)
     * 兼具栈迭代方法的高效，又像递归方法一样简洁易懂，更重要的是，这种方法对于前序、中序、后序遍历，能够写出完全一致的代码。
     * 用颜色来标记哪些节点是需要进栈的，那些是需要读取的。
     */

    public List<Integer> inorderTraversal4(TreeNode root) {
        // 用0表示白色，无需读取值；用1表示灰色，需要读取值

        // 定义一个栈结构
        Deque<Object[]> stack = new ArrayDeque<>();

        List<Integer> res = new ArrayList<>();

        stack.push(new Object[]{0, root});
        while (!stack.isEmpty()) {
            Object[] curObj = stack.pop();
            TreeNode curNode = (TreeNode) curObj[1];
            if (null == curNode) {
                continue;
            }
            boolean readVal = curObj[0].equals(0) ? false : true;
            // 当前节点不需要读取，则按遍历的逆序进栈，注意标记
            if (readVal) {
                res.add(curNode.val);
            } else {
                stack.push(new Object[]{0, curNode.right});
                stack.push(new Object[]{1, curNode});
                stack.push(new Object[]{0, curNode.left});
            }
        }

        return res;
    }

    public static void main(String[] args) {
        TreeNode right1 = new TreeNode(3, null, null);
        TreeNode right2 = new TreeNode(2, right1, null);
        TreeNode root = new TreeNode(1, null, right2);
        System.out.println(new Solution().inorderTraversal4(root));
    }
}
