package hot100.question105;

import java.util.HashMap;
import java.util.Map;

/**
 * #105 从前序与中序遍历序列构造二叉树
 */
public class Solution {
    /**
     * 方法一、递归 （这个题有迭代解，但是太复杂，题解都懒得看完，这里只写递归）
     *
     * 思路
     * 对于任意一颗树而言，前序遍历的形式总是
     * [ 根节点, [左子树的前序遍历结果], [右子树的前序遍历结果] ]
     * 即根节点总是前序遍历中的第一个节点。而中序遍历的形式总是
     * [ [左子树的中序遍历结果], 根节点, [右子树的中序遍历结果] ]
     * 只要我们在中序遍历中定位到根节点，那么我们就可以分别知道左子树和右子树中的节点数目。
     * 由于同一颗子树的前序遍历和中序遍历的长度显然是相同的，因此我们就可以对应到前序遍历的结果中，对上述形式中的所有左右括号进行定位。
     * 这样以来，我们就知道了左子树的前序遍历和中序遍历结果，以及右子树的前序遍历和中序遍历结果，
     * 我们就可以递归地对构造出左子树和右子树，再将这两颗子树接到根节点的左右位置。
     *
     * 细节
     * 在中序遍历中对根节点进行定位时，一种简单的方法是直接扫描整个中序遍历的结果并找出根节点，
     * 但这样做的时间复杂度较高。我们可以考虑使用哈希表来帮助我们快速地定位根节点。对于哈希映射中的每个键值对，
     * 键表示一个元素（节点的值），值表示其在中序遍历中的出现位置。在构造二叉树的过程之前，
     * 我们可以对中序遍历的列表进行一遍扫描，就可以构造出这个哈希映射。
     * 在此后构造二叉树的过程中，我们就只需要 O(1) 的时间对根节点进行定位了。
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {

        // 先构造一个哈希表，避免在中序遍历中找根节点的时候，需要每次都扫描一遍中序遍历列表
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        TreeNode root = recurBuild(preorder, inorder,
                0, preorder.length - 1, 0, inorder.length - 1, inorderMap);

        return root;
    }

    private TreeNode recurBuild(int[] preorder, int[] inorder,
                                int preStar, int preEnd, int inStar, int inEnd,
                                Map<Integer, Integer> inorderMap) {

        // TODO 递归结束条件，很容易遗漏
        if (preStar > preEnd || inStar > inEnd) {
            return null;
        }

        // 前序遍历的第一个节点一定是根节点
        int preRootIdx = preStar;

        // 从map中找到中序遍历中根节点的位置，从而确定左右子树的元素个数
        // 如果之前没有构造Map，那么这里每次都要查找一次中序遍历列表
        int inRootIdx = inorderMap.get(preorder[preRootIdx]);

        // 构造当前节点
        TreeNode curNode = new TreeNode(inorder[inRootIdx]);

        // 左子树元素数 = 中序遍历根节点下标 - 中序遍历起点
        int leftNodeNum = inRootIdx - inStar;
        // 右子树元素数 = 中序遍历终点 - 中序遍历根节点下标
        int rightNodeNum = inEnd - inRootIdx;

        // 在上面确定了左右子树的元素个数之后，就可以确定左右子树的元素范围(序列)，
        // 从而将左右子树看做一颗新树进行递归，重复上述过程
        curNode.left = recurBuild(preorder, inorder,
                preRootIdx + 1, preRootIdx + leftNodeNum,
                inStar, inRootIdx - 1, inorderMap);
        curNode.right = recurBuild(preorder, inorder,
                preRootIdx + leftNodeNum + 1, preEnd,
                inRootIdx + 1, inEnd, inorderMap);

        return curNode;
    }

    public static void main(String[] args) {
        int[] preorder = new int[]{3,9,20,15,7};
        int[] inorder = new int[]{9,3,15,20,7};

        Solution solution = new Solution();
        solution.buildTree(preorder, inorder);
    }
}
