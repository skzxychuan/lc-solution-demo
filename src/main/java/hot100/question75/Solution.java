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

    /**
     * 这道题用排序就有点low了
     * 单指针或者双指针，这里只写双指针
     * 时间复杂度0(n)比排序快很多，但只适用于少数有限值域的排序，比如此题只有0,1,2三种值
     * @param nums
     */
    public void sortColors2(int[] nums) {
        int p0 = 0, p1 = 0;
        int temp;
        for (int i = 0; i< nums.length; i++) {
            if (nums[i] == 0) {
                temp = nums[i];
                nums[i] = nums[p0];
                nums[p0] = temp;
                // 当p0落后p1时，此时会把p0位置的1交换出去，所以要把那个1再交换到p1
                if (p0 < p1) {
                    temp = nums[i];
                    nums[i] = nums[p1];
                    nums[p1] = temp;
                }
                p0++;
                p1++;
            } else if (nums[i] == 1) {
                temp = nums[i];
                nums[i] = nums[p1];
                nums[p1] = temp;
                p1++;
            }
        }
    }
}
