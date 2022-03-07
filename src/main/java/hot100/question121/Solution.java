package hot100.question121;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 121. 买卖股票的最佳时机
 *
 * TODO 这个题的最高赞题解总结了其他几个买卖股票类型题的题解，值得一看。
 */
public class Solution {

    /**
     * 方法一（自解）、暴力解（双循环）
     * TODO 页面上超出时间限制
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len <= 1) {
            return 0;
        }

        int max = 0;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                int income = prices[j] - prices[i];
                if (income > max) {
                    max = income;
                }
            }
        }

        return max > 0 ? max : 0;
    }

    /**
     * 方法二（官解方法二）、官方解法二没有说是动态规划，说是一次遍历，但其实就是动态规划
     * 直接看代码有点难懂，看下面的评论解释就知道了，其实就是动态规划优化空间后的结果。
     *
     * 动态规划类型的题的显著特点：
     * 1. 只要求输出最后的结果，不需要返回细节，比如这里只要求求最大收益，不要求求最大收益出现在哪两个下标。
     * 2. 无后效性，即新的状态只能从之前(已存在)的状态计算出来，不能依赖未来状态(还未计算)
     *
     * 定义状态：
     * 假设我们第i天卖出股票，那当天的最大收益取决于什么呢？取决于前i-1天的最小值（需要在最小值的时候买入）。
     * 那前i-1天的最小值取决于什么呢？ 等于 第i-1天的价格 与 i-1天前最小值 两者间的最小值，即:
     * min[i-1] = Math.min(prices[i-1], min[i - 1 -1])
     * 重新令i-1为i,因此我们定义状态dp[i]为前i天(包括i)的最小值，则有状态转移方程：
     * dp[i] = Math.min(prices[i], dp[i-1])
     *
     * 初始化：dp[0] = prices[0]
     *
     * 输出: dp[i]只代表截止第i天的最小值，还不是收益，因此还要计算最大收益
     *
     * 空间优化：见注释
     *
     */
    public int maxProfit2(int[] prices) {
        int len = prices.length;
        if (len <= 1) {
            return 0;
        }

        // 状态表，动态规划就是填表
        int[] dp = new int[len];
        dp[0] = prices[0];

        int maxIncome = 0;

        // 假设在第i天卖出(可以当天买当天卖，只不过没有收益而已)
        for (int i = 1; i < len; i++) {
            //前i天的最小值
            dp[i] = Math.min(prices[i], dp[i - 1]);
            //第i天卖出的最大收益 = 第i天的价格 - 前i天最小值
            int income = prices[i] - dp[i];
            if (income > maxIncome) {
                maxIncome = income;
            }
        }

        /**
         * 空间优化：
         * 看上面的代码，dp[i]的计算其实只用了dp[i - 1]，用来不断的计算新的dp[i]，
         * 其他的部分都没有用到，那么完全可以用一个变量来代替状态表数组。
         * 空间优化之后，其实就是官方题解方法二了。
         */
//        int minPrice = prices[0];
//        int maxIncome = 0;
//        for (int i = 0; i < len; i++) {
//            minPrice = Math.min(prices[i], minPrice);
//            int income = prices[i] - minPrice;
//            if (income > maxIncome) {
//                maxIncome = income;
//            }
//        }

        return maxIncome;
    }

    /**
     * 方法三(精选题解)、单调栈，同类型题还有 #84 #85
     * 详细思路见题解.
     * TODO 这个题的思想需要自己多理解一下,且速度没有动态规划快
     *
     * 一眼看过去，这个题本质就是要求某个数与其右边最大的数的差值，这符合了单调栈的应用场景:
     * 当你需要高效率查询某个位置左右两侧比他大（或小）的数的位置的时候，于是我就用单调栈解决了
     *
     * 这里我们维护一个单调增的栈，要赚钱嘛，肯定单调增。
     * 首先讲下维护单调栈的 具体思路：
     * 在 pricesprices 数组的末尾加上一个 哨兵(也就是一个很小的元素，这里设为 0)，就相当于作为股市收盘的标记(后面就清楚他的作用了)
     * 假如栈空或者入栈元素大于栈顶元素，直接入栈
     * 假如入栈元素小于栈顶元素则循环弹栈，直到入栈元素大于栈顶元素或者栈空
     * 在每次弹出的时候，我们拿弹出值与买入的值(也就是栈底)做差，维护一个最大值。
     * @return
     */
    public int maxProfit3(int[] prices) {
        int len = prices.length;
        if (len <= 1) {
            return 0;
        }

        int[] maxIncome = new int[]{0};

        // 定义一个栈，把他维护成单调递增
        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < prices.length; i++) {
            handle(stack, prices[i], maxIncome);
        }

        // TODO 这里很容易遗漏：最后还要push一个足够小的哨兵进去，触发stack中剩余的元素出栈进行计算
        // 题目的输入是[0,10000],所以-1就可以
        handle(stack, -1, maxIncome);

        return maxIncome[0];
    }

    private void handle(Deque<Integer> stack, int curPrice, int[] maxIncome) {
        // 栈为空直接入栈
        if (stack.isEmpty()) {
            stack.push(curPrice);
            return;
        }

        // 当前元素大于栈顶，直接入栈
        if (curPrice > stack.peek()) {
            stack.push(curPrice);
            return;
        }

        // 当前元素小于栈顶，循环弹出大于当前元素的，注意要判断弹出后是否为空
        while (!stack.isEmpty() && curPrice <= stack.peek()) {
            // 收益就是栈顶 - 栈低
            Integer top = stack.pop();
            Integer bottom = stack.isEmpty() ? top : stack.peekLast();
            Integer income = top - bottom;
            if (income > maxIncome[0]) {
                maxIncome[0] = income;
            }
        }
        stack.push(curPrice);
    }
}
