package hot100.question128;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * #128 最长连续序列
 */
public class Solution {

    /**
     * 方法一（自解）、哈希表
     * TODO 超出时间限制，题目要求时间复杂度为O(n)
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return 0;
        }

        // 把数组转成一个哈希表，用来验证是否存在
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // 只有key有意义，value随便给
            map.put(nums[i], 0);
        }

        // 最大长度
        int maxLen = 1;

        for (int i = 0; i < nums.length; i++) {
            int curNum = nums[i];
            int curMaxLen = 0;
            while (null != map.get(curNum)) {
                curMaxLen++;
                curNum -= 1;
            }
            if (curMaxLen > maxLen) {
                maxLen = curMaxLen;
            }
        }

        return maxLen;
    }


    /**
     * 方法二（官解）、哈希表
     * 官方题解是递增，这里逆着写，递减
     * TODO 这里看起来有两层循环，但是能进内循环的数，就一定会在外循环跳过，实际只有一层循环
     * TODO 也给出了一个结论：双循环的时间复杂度未必是O(n^2),特殊情况也能是O(n)
     * @param nums
     * @return
     */
    public int longestConsecutive2(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return 0;
        }

        //TODO 把数组转成一个哈希表，用来验证是否存在，没必要非要用map，用set也可以
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        // 最大长度
        int maxLen = 1;
        for (int i = 0; i < nums.length; i++) {
            // TODO 这个赋值操作放到下面去能多击败30%的用户，所以跳循环最好一开始就跳，节省时间
//            int curNum = nums[i];

            // 当前数如果还有下一个数，那么没必要进行计算
            if (set.contains(nums[i] + 1)) {
                continue;
            }
            int curNum = nums[i];
            int curMaxLen = 0;
            while (set.contains(curNum)) {
                curMaxLen++;
                curNum -= 1;
            }
            if (curMaxLen > maxLen) {
                maxLen = curMaxLen;
            }
        }

        return maxLen;
    }

    /**
     * 方法三（精选题解）、动态规划 + 哈希表
     * TODO 这种只求最后统计结果(最长连续序列的长度)，而不是求具体细节的题(最长连续序列是哪一个),都可以尝试动态规划
     * TODO 具体思路见页面的评论解释, 整个过程就像是在插空
     * @param nums
     * @return
     */
    public int longestConsecutive3(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return 0;
        }

        // 定义一个hash, 用来保存端点值所处连续序列的长度
        Map<Integer, Integer> map = new HashMap<>();

        int maxLen = 0;
        for (int i = 0; i < nums.length; i++) {
            // 不存在则插空
            if (!map.containsKey(nums[i])) {
                // 插空
                map.put(nums[i], 1);
                // 存在左端点，则获取左端点所处连续序列的长度
                int left = map.getOrDefault(nums[i] - 1, 0);
                // 存在右端点，则获取右端点所处连续序列的长度
                int right = map.getOrDefault(nums[i] + 1, 0);
                // 新的连续序列的长度，比如左123 右567，插入4, 则1234567 = 3 + 1 + 3
                int newLen = left + 1 + right;
                // 将新长度赋值给端点值，这里也仅需要更新端点值
                // TODO 因为中间的已经没有“空位”可以插入了，他们的值可以不用管了
                map.put(nums[i] - left, newLen);
                map.put(nums[i] + right, newLen);
                maxLen = Math.max(newLen, maxLen);
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{100,4,200,1,3,2};
        new Solution().longestConsecutive3(nums);
    }

}
