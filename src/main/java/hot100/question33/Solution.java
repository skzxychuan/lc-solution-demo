package hot100.question33;

public class Solution {

    public int search(int[] nums, int target) {
        if (null == nums) {
            return -1;
        }

        if (nums.length == 1) {
            return nums[0] == target ? 0 : -1;
        }

        int left = 0;
        int right = nums.length - 1;
        // 核心思想：一个升序旋转后的数组任意切一刀，一定一半是完全有序的，另一半是部分有序的
        while (left <= right) {
            int mid = (left + right) / 2;
            // 切分点命中
            if (nums[mid] == target) {
                return mid;
            }
            // 切分点未命中，且无法再切分时表示找不到目标
            if (left == right) {
                break;
            }

            if (nums[0] <= nums[mid]) { //左半边有序，这里为什么是<=而不是<，思考一下数组长度为2的特殊情况就可以了
                // 判断target是否在有序的左边
                if (target >= nums[left] && target <= nums[mid]) {
                    right = mid;
                } else {    // target在无序的右半边
                    left = mid + 1;
                }
            } else { // 右半边有序
                // 判断target是否在有序的右边
                if (target >= nums[mid + 1] && target <= nums[right]) {
                    left = mid + 1;
                } else {    // target在无序的左半边
                    right = mid;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {5, 1, 3};
        int target = 3;
        System.out.println(new Solution().search(nums, target));
    }
}
