package hot100.question139;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * #139 单词拆分
 */
public class Solution {

    /**
     * 方法一（官方解）、动态规划
     * 具体思路见页面题解
     *
     * 定义状态：dp[i]为前i个字符[0,i-1]是否可以由字典组成
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        // 单词的哈希表
        Set<String> words = new HashSet<>(wordDict);

        // 动态规划状态表，动态规划就是填表，这里+1是将字符串前面看做有一个隐式的""空字符，方便做初始化
        boolean[] dp = new boolean[s.length() + 1];

        // 初始化，令dp[0]=true
        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            // j为可能的分割点
            for (int j = 0; j < i; j++) {
                if (dp[j] && words.contains(s.substring(j, i))) {
                    // 找到了一个分割点就可以返回了
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

}
