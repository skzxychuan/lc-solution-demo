package hot100.question5;

/**
 * 热题100，#5，最长回文字串：
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *
 * 示例 1：
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 *
 * 示例 2：
 * 输入: "cbbd"
 * 输出: "bb"
 *
 * 解法有很多：1.动态规划；2.中心扩散；3.马拉车算法，这里只练习前两种
 */
public class Solution {
    /**
     * 动态规划解法（最重要的是前两步）：
     * 1. 思考把什么定义为“状态”
     * 2. 思考状态转移方程（注意状态的转移必须要是“无后效性”的，即新的状态只能依赖已有的状态，而不能依赖还未计算出来的状态）
     * 3. 思考初始化
     * 4. 思考输出
     * 5. 思考优化（会降低代码的可读性，可以没有）
     * 「动态规划」方法依然是「空间换时间」思想的体现，常见的解决问题的过程很像在「填表」。
     * 「动态规划」的一个关键的步骤是想清楚「状态如何转移」。事实上，「回文」天然具有「状态转移」性质。
     * 一个回文去掉两头以后，剩下的部分依然是回文（这里暂不讨论边界情况）；
     * 即：在头尾字符相等的情况下，里面子串的回文性质据定了整个子串的回文性质，这就是状态转移。因此可以把「状态」定义为
     * 原字符串的一个子串是否为回文子串。
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        // 参数检测
        if (s == null || s.length() < 2) {
            return s;
        }
        int length = s.length();

        // 没有必要每次都截取子串，只保留起始位置和最大长度即可
        // 注意这个地方不能初始化为 Integer.MIN_VALUE，否则当输入类似"ac"的字符串时，会导致最后的return语句下标越界.
//        int maxLen = Integer.MIN_VALUE;
        int maxLen = 1;
        // 注意这个地方不能初始化为 -1，否则当输入类似"ac"的字符串时，会导致最后的return语句下标越界.
//        int startIndex = -1;
        int startIndex = 0;

        // s.charAt(i) 每次都会检查数组下标越界，因此先转换成字符数组,这样快一些
        char[] charArray = s.toCharArray();

        // 第一步：思考什么是状态，回文去掉两端后，内部依然要是回文，因此可以把状态定义成字符串是否是回文
        // 动态规划其实就是填表，这里dp[i][j]表示s[i,j]（注意是闭区间）是否是回文，由于i<j，所以填表也只填上半区(i为行，j为列)
        boolean dp[][] = new boolean[length][length];

        // 对角线上全为true，甚至都没必要填
        for (int i = 0; i < length; i++) {
            dp[i][i] = true;
        }

        // 第二步：思考状态转移方程：dp[i][j]为true的条件是：dp[i][j] = s[i] == s[j] && dp[i + 1][j - 1]
        // 开始正式填表，注意dp[i][j]依赖dp[i + 1][j - 1]，而dp[i + 1][j - 1]，出现在dp[i][j]的"左下方"
        // 所以填表的时候一定要注意，填dp[i][j]时，dp[i + 1][j - 1]必须在之前就已经填好了，即已经被计算出来了(无后效性)
        // 所以，在枚举所有子串的时候，两次for循环的遍历次序是有讲究的
        for (int j = 1; j < length; j++) {
            for (int i = 0; i < j; i++) {
                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    /**
                     * 看到 dp[i + 1][j - 1] 就得考虑边界情况。
                     * 边界条件是：表达式 [i + 1, j - 1] 不构成区间，即长度严格小于 2，即 j - 1 - (i + 1) + 1 < 2 ，整理得 j - i < 3。
                     * 这个结论很显然：j - i < 3 等价于 j - i + 1 < 4，即当子串 s[i..j] 的长度等于 2 或者等于 3 的时候，其实只需要判断一下头尾两个字符是否相等就可以直接下结论了。
                     * 如果子串 s[i + 1..j - 1] 只有 1 个字符，即去掉两头，剩下中间部分只有 1 个字符，显然是回文；
                     * 如果子串 s[i + 1..j - 1] 为空串，那么子串 s[i, j] 一定是回文子串。
                     * 因此，在 s[i] == s[j] 成立和 j - i < 3 的前提下，直接可以下结论，dp[i][j] = true，否则才执行状态转移。
                     */
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 只要dp[i][j] = true, 就说明s[i,j]是回文，此时记录最大长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    startIndex = i;
                }
            }
        }

        return s.substring(startIndex, startIndex + maxLen);
    }

    /**
     * 方法二：中心扩散法
     * 暴力法采用双指针两边夹，验证是否是回文子串。
     * 除了枚举字符串的左右边界以外，比较容易想到的是枚举可能出现的回文子串的“中心位置”，从“中心位置”尝试尽可能扩散出去，得到一个回文串。
     * 因此中心扩散法的思路是：遍历每一个索引，以这个索引为中心，利用“回文串”中心对称的特点，往两边扩散，看最多能扩散多远。
     * 枚举“中心位置”时间复杂度为 O(N)，从“中心位置”扩散得到“回文子串”的时间复杂度为 O(N)，因此时间复杂度可以降到 O(N^2)
     *
     * 在这里要注意一个细节：回文串在长度为奇数和偶数的时候，“回文中心”的形式是不一样的。
     * 奇数回文串的“中心”是一个具体的字符，例如：回文串 "aba" 的中心是字符 "b"；
     * 偶数回文串的“中心”是位于中间的两个字符的“空隙”，例如：回文串串 "abba" 的中心是两个 "b" 中间的那个“空隙”。
     * 我们可以设计一个方法，兼容以上两种情况：
     * 1、如果传入重合的索引编码，进行中心扩散，此时得到的回文子串的长度是奇数；
     * 2、如果传入相邻的索引编码，进行中心扩散，此时得到的回文子串的长度是偶数。
     * @param s
     * @return
     */
    public static String longestPalindrome2(String s) {
        //参数检测
        if (s == null || s.length() < 2) {
            return s;
        }

        int maxLen = Integer.MIN_VALUE;
        String res = null;

        for (int i = 0; i < s.length() - 1; i++) {
            String oddStr = centerSpread(s, i, i);
            String evenStr = centerSpread(s, i, i + 1);
            String curMaxLenStr = oddStr.length() > evenStr.length() ? oddStr : evenStr;
            if (curMaxLenStr.length() > maxLen) {
                maxLen = curMaxLenStr.length();
                res = curMaxLenStr;
            }
        }
        return res;
    }

    public static String centerSpread(String s, int start, int end) {
        int length = s.length();
        char[] charArr = s.toCharArray();

        while (start >=0 && end < length) {
            if (charArr[start] == charArr[end]) {
                start--;
                end++;
            } else {
                break;
            }
        }

        // 这里要小心，跳出 while 循环时，要么满足 s.charAt(i) != s.charAt(j)，要么i,j中的一个越界，因此不能取 i，不能取 j
        return s.substring(start + 1, end);
    }

    public static boolean isPalindrome(String sub) {
        int start = 0;
        int end = sub.length() - 1;
        while (start <= end) {
            if (sub.charAt(start) != sub.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public static void main(String[] args) {
        String str = "ac";
        System.out.println(longestPalindrome(str));
        System.out.println(longestPalindrome2(str));
    }
}
