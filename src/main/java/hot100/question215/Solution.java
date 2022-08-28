package hot100.question215;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * #215 数组中的第K个最大元素(要求时间复杂度为O(n))
 */
public class Solution {

    /**
     * 最高赞题解方法一：
     * 题目的核心理解：由于找第 K 大元素，其实就是整个数组排序以后后半部分最小的那个元素。
     * 因此可以利用快速排序中的 分治 与 pivot 机制
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {

        int len = nums.length;
        int target = len - k;

        // 这里不用像快排一样去递归，只需要一直去找到倒数第K个pivot点，
        // 直到找到为止，所以时间复杂度是0(n)
        int left = 0;
        int right = nums.length - 1;
        while (true) {
            int pivotIndex = partition(nums, left, right);

            // 倒数第K个点的下标刚好是len - k, 找到了就返回，没找到就一直找
            if (pivotIndex == target) {
                return nums[pivotIndex];
            } else if (pivotIndex < target) {
                // 重新从pivot的右边去找
                left = pivotIndex + 1;
            } else {
                // 从pivot左边找
                right = pivotIndex - 1;
            }
        }
    }

    private int partition(int[] nums, int left, int right) {
        // 先选一个pivotValue，为了避免极端情况(已经是正序或者逆序)，随机选
        int randomIndex = left + new Random().nextInt(right - left + 1);

        // 默认取第一个作为pivot值，先将随机选的交换到第一个
        swap(nums, left, randomIndex);
        int pivotValue = nums[left];

        while (left < right) {
            // 一次值交换一个半边
            while (left < right && nums[right] >= pivotValue) {
                right--;
            }
            swap(nums, left, right);
            while (left < right && nums[left] <= pivotValue) {
                left++;
            }
            swap(nums, left, right);
        }

        // 返回pivot的最终下标
        return left;
    }

    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    /**
     * 最高赞题解方法二：优先队列(堆, java集合中没有直接的堆类，用优先队列实现，堆本来也是一种特殊队列)
     * 但是 时间复杂度：O(NlogK)  不太符合题意
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest2(int[] nums, int k) {
        int len = nums.length;
        
        // 由于找第 K 大元素，其实就是整个数组排序以后后半部分最小的那个元素。
        // 只需要创建一个K个元素的小顶堆，默认小顶堆
        PriorityQueue<Integer> heap = new PriorityQueue<>(k);

        // 先把前K个元素进堆，把堆填满
        for (int i = 0; i < k; i++) {
            heap.offer(nums[i]);
        }

        // 再把后面的n-k个元素入堆，比堆顶小的元素就没必要入堆了，入堆了也不会改变堆顶，而且还会导致内部结构调整
        for (int i = k; i < len; i++) {
            if (nums[i] > heap.peek()) {
                // 堆的容量是K，比堆顶大的数要入堆，那就要出去一个，刚好把最小的出去，这里有点滑动窗口的味道
                heap.poll();
                heap.offer(nums[i]);
            }
        }

        return heap.peek();
    }

    /**
     * 最高赞题解方法三：上面的方法二还可以优化，两个for循环可以写成一个（只是写法优化，速度没有方法二快）
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest3(int[] nums, int k) {
        int len = nums.length;

        PriorityQueue<Integer> heap = new PriorityQueue<>();

        for (int num : nums) {
            heap.offer(num);
            // 类似于滑动窗口，超过K个元素的时候，出去一个，最后的堆顶就是我们想要的
            if (heap.size() > k) {
                heap.poll();
            }
        }

        return heap.peek();
    }
}
