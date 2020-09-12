package hot100.question22;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 热题100，#22. 括号生成：
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * 直接看java最高赞题解吧，那个树形图很重要。
 * 可以和#17电话号码字母组合做一个比较找找这类题类似的"感觉"
 * 方法一：回溯算法（深度优先）
 * 方法二：广度优先
 * 方法三：动态规划
 */
public class Solution {

    /**
     * 方法一：深度优先
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();

        //参数检测
        if (n <= 0) {
            return res;
        }

        //注意这里以空字符作初始值的技巧
        dfs("", n, n, res);

        return res;
    }

    /**
     *
     * @param curStr 结果字符串
     * @param left 剩下未使用的左括号数
     * @param right 剩下未使用的右括号数
     */
    private void dfs(String curStr, int left, int right, List<String> res) {
        //递归结束条件：左右括号都用完了，可以返回结果了
        if (left == 0 && right == 0) {
            res.add(curStr);
            return;
        }

        //剩下的左括号数大于右括号数，剪枝
        if (left > right) {
            return;
        }

        //有个坑，就是left - 1/right - 1，千万不能写成 --left和--right，因为回栈的时候，这个数值变了，就会影响结果了。
        //左括号没用完，深度优先左括号
        if (left > 0) {
            dfs(curStr + "(", left - 1, right, res);
        }

        //右括号没用完，深度优先右括号
        if (right > 0) {
            dfs(curStr + ")", left, right - 1, res);
        }
    }

    /**
     * 方法二：广度优先遍历
     * 通过编写广度优先遍历的代码，读者可以体会一下，为什么搜索几乎都是用深度优先遍历（回溯算法）。
     *
     * 广度优先遍历，得程序员自己编写结点类，显示使用队列这个数据结构。深度优先遍历的时候，就可以直接使用系统栈，
     * 在递归方法执行完成的时候，系统栈顶就把我们所需要的状态信息直接弹出，而无须编写结点类和显示使用栈。
     * @param n
     * @return
     */
    public List<String> generateParenthesis2(int n) {
        List<String> res = new ArrayList<>();

        if (n <= 0) {
            return res;
        }

        //二叉树是特殊的连通图，二叉树的层序遍历(广度优先)，图的广度优先，都要借助一个队列
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node("", n, n));

        while (!queue.isEmpty()) {
            Node curNode = queue.poll();
            if (curNode.left == 0 && curNode.right == 0) {
                res.add(curNode.curStr);
            }

            if (curNode.left > curNode.right) {
                continue;
            }

            if (curNode.left > 0) {
                queue.offer(new Node(curNode.curStr + "(", curNode.left - 1, curNode.right));
            }

            if (curNode.right > 0) {
                queue.offer(new Node(curNode.curStr + ")", curNode.left, curNode.right - 1));
            }
        }

        return res;
    }

    private class Node {
        private String curStr;
        private int left;
        private int right;

        public Node(String curStr, int left, int right) {
            this.curStr = curStr;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 方法三：动态规划
     * 第 1 步：定义状态 dp[i]：使用 i 对括号能够生成的组合。
     * 注意：每一个状态都是列表的形式。
     *
     * 第 2 步：状态转移方程：
     * i 对括号的一个组合，在 i - 1 对括号的基础上得到，这是思考 “状态转移方程” 的基础；
     * i 对括号的一个组合，一定以左括号 "(" 开始，不一定以 ")" 结尾。为此，我们可以枚举新的右括号 ")" 可能所处的位置，得到所有的组合；
     * 枚举的方式就是枚举左括号 "(" 和右括号 ")" 中间可能的合法的括号对数，而剩下的合法的括号对数在与第一个左括号 "(" 配对的右括号 ")" 的后面，这就用到了以前的状态。
     * 状态转移方程是：
     * dp[i] = "(" + dp[可能的括号对数] + ")" + dp[剩下的括号对数]
     * “可能的括号对数” 与 “剩下的括号对数” 之和得为 i - 1（感谢 @xuyik 朋友纠正了我的错误），故 “可能的括号对数” j 可以从 0 开始，最多不能超过 i， 即 i - 1；
     * “剩下的括号对数” + j = i - 1，故 “剩下的括号对数” = i - j - 1。
     * 整理得：
     * dp[i] = "(" + dp[j] + ")" + dp[i- j - 1] , j = 0, 1, ..., i - 1
     *
     * 第 3 步： 思考初始状态和输出：
     * 初始状态：因为我们需要 0 对括号这种状态，因此状态数组 dp 从 0 开始，0 个括号当然就是 [""]。
     * 输出：dp[n] 。
     * 这个方法暂且就叫它动态规划，这么用也是很神奇的，它有下面两个特点：
     * 1、自底向上：从小规模问题开始，逐渐得到大规模问题的解集；
     * 2、无后效性：后面的结果的得到，不会影响到前面的结果。

     * 这个动态规划没太看懂，比较难想到，应该不是通用解法，这里就不练习了，直接去官网看java最高赞题解吧
     * 下面是该题解的一些评论，可以看出这个动态规划比较难想到。
     *
     * “这个动态鬼话绝了，挑出一对括号作为参照物，从小到大扩展遍历这个参照括号内和外的所有可能？”
     * “对的哈，后面的计算参考了前面计算的结果。也可以不扯上「动态规划」，就说「空间换时间」也是一种可以解释的通的思路。
     * 很多「空间换时间」的做法，都可以解释成为「动态规划」。”
     *
     * “这题目的 动态规划好难理解啊”
     * “不要看这个解法的名字，思路就是先求 1 对括号的解法，在这个基础上求 2 对括号的解法，过程是枚举每一个右括号可能出现的位置。
     * 这个解法不是通解，暂时可以不用掌握，我也是看官解和精选解法以后才写的自己的理解。把这个解法换成「空间换时间」是完全可以的。”
     * @param n
     * @return
     */
    public List<String> generateParenthesis3(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }
        // 这里 dp 数组我们把它变成列表的样子，方便调用而已
        List<List<String>> dp = new ArrayList<>(n);

        List<String> dp0 = new ArrayList<>();
        dp0.add("");
        dp.add(dp0);

        for (int i = 1; i <= n; i++) {
            List<String> cur = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                List<String> str1 = dp.get(j);
                List<String> str2 = dp.get(i - 1 - j);
                for (String s1 : str1) {
                    for (String s2 : str2) {
                        // 枚举右括号的位置
                        cur.add("(" + s1 + ")" + s2);
                    }
                }
            }
            dp.add(cur);
        }
        return dp.get(n);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.generateParenthesis(4));
        System.out.println(solution.generateParenthesis2(4));
        System.out.println(solution.generateParenthesis3(4));
    }
}
