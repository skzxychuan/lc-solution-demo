package hot100.question98;

/**
 * #98 验证二叉搜索树
 */
public class Solution {

    /**
     * 方法一、前序遍历
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {

        // 用来保存结果
        Boolean[] res = new Boolean[]{true};

        beforeScan(root, res, Long.MIN_VALUE, Long.MAX_VALUE);

        return res[0];
    }

    private void beforeScan(TreeNode curNode, Boolean[] res, long min, long max) {
        if (null == curNode || res[0].equals(false)) {
            return;
        }

        if (curNode.val <= min || curNode.val >= max) {
            res[0] = false;
        }

        beforeScan(curNode.left, res, min, curNode.val);
        beforeScan(curNode.right, res, curNode.val, max);
    }

    /**
     * 方法二、中序遍历
     * 如果是二叉搜索树，那么它的中序遍历一定是个升序序列，即当前节点值一定大于前一个节点值
     */

    // 这里一开始加了static，结果到页面提交就结果不对了，lc页面似乎对静态变量的支持不够好
    private long preVal = Long.MIN_VALUE;
    private boolean res = true;

    public boolean isValidBST2(TreeNode root) {
        midScan(root);
        return res;
    }

    private void midScan(TreeNode curNode) {
        if (null == curNode || res == false) {
            return;
        }

        midScan(curNode.left);
        if (curNode.val <= preVal) {
            res = false;
        }
        preVal = Integer.valueOf(curNode.val).longValue();
        midScan(curNode.right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(0, null, null);
        System.out.println(new Solution().isValidBST2(root));
    }
}
