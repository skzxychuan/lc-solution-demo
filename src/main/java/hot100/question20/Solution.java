package hot100.question20;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 热题100，#20，有效括号：
 * 1. 栈先入后出特点恰好与本题括号排序特点一致，即若遇到左括号入栈，遇到右括号时将对应栈顶左括号出栈，则遍历完所有括号后 stack 仍然为空；
 * 2. 建立哈希表 dic 构建左右括号对应关系：keykey 左括号，valuevalue 右括号；这样查询 22 个括号是否对应只需 O(1)O(1) 时间复杂度；
 * 3. 建立栈 stack，遍历字符串 s 并按照算法流程一一判断。
 *
 * 一开始想这个题目的时候，想到了栈，后面又想到动态规划和中心扩散去了，以为和#5回文很像，这是一道简单难度的题，不要想那么复杂。
 */
public class Solution {
    //左右括号的哈希表
    Map<Character, Character> map = new HashMap<Character, Character>(){
        {
            put('(', ')');
            put('{', '}');
            put('[', ']');
        }
    };

    public boolean isValid(String s) {
        //参数检测，题目规定空字符串是有效的
        //不以左括号开头肯定无效，奇数长度肯定无效
        if (null == s || !map.containsKey(s.charAt(0)) || s.length() % 2 != 0) {
            return false;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            Character curChar = s.charAt(i);
            //是左括号则进栈
            if (map.containsKey(curChar)) {
                stack.push(curChar);
            }
            //是右括号则判断是否与栈顶元素匹配
            if (map.values().contains(curChar)) {
                //注意要检测栈是否为空再pop,否则会报异常，比如"(){}}",遇到右括号的时候，stack已经是空了
                if (stack.empty() || !map.get(stack.pop()).equals(curChar)) {
                    return false;
                }
            }
        }
        // 注意这里要检查，即使循环完了，还要判断栈是否是空的，空的才算true，比如"(("这种情况，不会在上面的代码return false.
        return stack.empty();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isValid("(){}}{"));
        System.out.println(solution.isValid("{[{}()]{}()}"));
    }
}
