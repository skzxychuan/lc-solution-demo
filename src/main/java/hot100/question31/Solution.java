package hot100.question31;

public class Solution {

    public void nextPermutation(int[] nums) {
        if (null == nums || nums.length == 1) {
            return;
        }

        for (int i = nums.length - 1; i >= 1; i--) {
            if (nums[i - 1] < nums[i]) {
                for (int j = nums.length - 1; j > i - 1; j--) {
                    if (nums[j] > nums[i - 1]) {
                        // 交换
                        int temp = nums[i - 1];
                        nums[i - 1] = nums[j];
                        nums[j] = temp;
                        // 重新升序[i,n)
                        for (int k = 0; k < nums.length - 1 - i; k++) {
                            for (int l = i; l < nums.length - 1 - k; l++) {
                                if (nums[l] > nums[l + 1]) {
                                    int temp2 = nums[l];
                                    nums[l] = nums[l + 1];
                                    nums[l + 1] = temp2;
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }

        // 能到这里说明已经是降序
        int i = 0, j = nums.length - 1;
        while(i < j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,3,2};
        new Solution().nextPermutation(nums);
        for (int num : nums) {
            System.out.print(num);
        }

    }
}
