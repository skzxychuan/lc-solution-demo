package hot100.question75;

/**
 * #75 颜色分类
 */
public class Solution {

    /**
     * 好像排序就可以了？
     * TODO 确实可以通过，但是只击败了8%的用户
     *
     * TODO 题目的进阶要求：
     * 你可以不使用代码库中的排序函数来解决这道题吗？
     * 你能想出一个仅使用常数空间的一趟扫描算法吗？
     * @param nums
     */
    public void sortColors(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }
}
