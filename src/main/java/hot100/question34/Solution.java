package hot100.question34;

public class Solution {
    public int[] searchRange(int[] nums, int target) {
        if (null == nums || 0 == nums.length) {
            return new int[]{-1, -1};
        }

        if (1 == nums.length) {
            return nums[0] == target ? new int[]{0, 0} : new int[]{-1, -1};
        }

        int start = 0;
        int end = nums.length;  //注意这里是length不是length - 1
        int left = 0;
        int right = nums.length - 1;
        // 第一轮，找到第一个>=target的下标
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] >= target) {
                start = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        // 第二轮，找到第一个>target的下标然后-1
        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target) {
                end = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (start <= end && nums[start] == target && nums[end - 1] == target) {
            return new int[]{start, end - 1};
        }
        return new int[]{-1, -1};
    }
}
