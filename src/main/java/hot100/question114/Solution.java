package hot100.question114;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * #114 二叉树展开为链表
 */
public class Solution {

    /**
     * 方法一（官方题解）、先序遍历一次，返回一个先序遍历的新列表, 再修改列表中的TreeNode成员的指针
     * 迭代法，最好和#94的中序遍历迭代做下对比，比较前序遍历的迭代和中序遍历的迭代有什么不同, 最好在扩展一下后续遍历的迭代。
     * TODO 这里虽然也算是原地修改，但是使用了辅助list，空间复杂度是0(n)，而题目进阶要求空间复杂度O(1)
     * @param root
     */
    public void flatten(TreeNode root) {
        if (null == root) {
            return;
        }

        // 用来保存前序遍历的结果
        List<TreeNode> list = new ArrayList<>();

        // 定义一个栈用迭代的方式实现前序遍历
        Deque<TreeNode> stack = new ArrayDeque<>();
//        stack.push(root);
//        while (!stack.isEmpty()) {
//            TreeNode curNode = stack.pop();
//            list.add(curNode);
//            if (null != curNode.right) {
//                stack.push(curNode.right);
//            }
//            if (null != curNode.left) {
//                stack.push(curNode.left);
//            }
//        }

        /* --------上面的迭代过程也可以写成这样，这就和#94中序遍历迭代的代码类似了-------- */
        // TODO 强烈建议再看看方法二，就知道这两种迭代的区别了：上面的迭代每次都把右节点入栈了，不需要通过根节点去获取右节点
        while (null != root || !stack.isEmpty()) {
            while (null != root) {
                list.add(root);
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // TODO 这种迭代需要通过根节点(上一个节点)访问右节点，stack中没有用过push(root.right)
            // TODO 如果学方法二一样维护一个 prev, 修改prev的left和right，那么真正的右子树就丢失了
            root = root.right;
        }

        // 重新修改前序列表中的节点的指针
        for (int i = 1; i < list.size(); i++) {
            TreeNode preNode = list.get(i - 1);
            TreeNode curNode = list.get(i);
            preNode.right = curNode;
            preNode.left = null;
        }
    }

    /**
     * 方法二（官方题解）、迭代先序遍历的同时将树修改为链表 (方法一是先序遍历完，再修改为链表)
     * TODO 好好对比一下方法一，看看两种迭代的差异，和方法一一样，虽然是原地修改，但空间复杂度是O(n)
     *
     * 使用方法一的前序遍历，由于将节点展开之后会破坏二叉树的结构而丢失子节点的信息，因此前序遍历和展开为单链表分成了两步。
     *
     * 之所以会在破坏二叉树的结构之后丢失子节点的信息，是因为在对左子树进行遍历时，没有存储右子节点的信息，
     * 在遍历完左子树之后才获得右子节点的信息。只要对前序遍历进行修改，在遍历左子树之前就获得左右子节点的信息，
     * 并存入栈内，子节点的信息就不会丢失，就可以将前序遍历和展开为单链表同时进行。
     *
     * 该做法不适用于递归实现的前序遍历，只适用于迭代实现的前序遍历。修改后的前序遍历的具体做法是，
     * 每次从栈内弹出一个节点作为当前访问的节点，获得该节点的子节点，如果子节点不为空，则依次将右子节点和左子节点压入栈内（注意入栈顺序）。
     *
     * 展开为单链表的做法是，维护上一个访问的节点 prev
     */
    public void flatten2(TreeNode root) {
        if (root == null) {
            return;
        }
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        stack.push(root);
        TreeNode prev = null;

        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            if (prev != null) {
                prev.left = null;
                prev.right = curr;
            }
            TreeNode left = curr.left, right = curr.right;
            if (right != null) {
                //TODO 将右节点单独进栈了，与另一种迭代相比，不再需要通过回退到根节点(上一个节点)来访问右节点
                stack.push(right);
            }
            if (left != null) {
                stack.push(left);
            }
            prev = curr;
        }
    }

    /**
     * 方法三（官方题解）、寻找前驱节点
     * 前两种方法都借助前序遍历，前序遍历过程中需要使用栈存储节点，导致空间复杂度是O(n)。有没有空间复杂度是 O(1) 的做法呢？
     *
     * 注意到前序遍历访问各节点的顺序是根节点、左子树、右子树。如果一个节点的左子节点为空，则该节点不需要进行展开操作。
     * 如果一个节点的左子节点不为空，则该节点的左子树中的最后一个节点被访问之后，该节点的右子节点被访问。
     * 该节点的左子树中最后一个被访问的节点是左子树中的最右边的节点，也是该节点右子节点的前驱节点。
     * 因此，问题转化成寻找当前节点的前驱节点。
     *
     * 具体做法是，对于当前节点，如果其左子节点不为空，则在其左子树中找到最右边的节点，作为前驱节点，
     * 将当前节点的右子节点赋给前驱节点的右子节点，然后将当前节点的左子节点赋给当前节点的右子节点，
     * 并将当前节点的左子节点设为空。对当前节点处理结束后，继续处理链表中的下一个节点，直到所有节点都处理结束。
     *
     * @param root
     */
    public void flatten3(TreeNode root) {
        TreeNode curNode = root;
        while (null != curNode) {
            if (null != curNode.left) {
                // 找到左子树的最右节点，它是当前节点的右子节点的前驱
                TreeNode predecessor = curNode.left;
                while (null != predecessor.right) {
                    predecessor = predecessor.right;
                }
                // 前驱节点指向下一个节点
                predecessor.right = curNode.right;
                // 当前节点就是它的左子节点的前驱节点
                TreeNode left = curNode.left;
                curNode.left = null;
                curNode.right = left;
            }
            curNode = curNode.right;
        }
    }
}
