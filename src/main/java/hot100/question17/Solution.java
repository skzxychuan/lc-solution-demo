package hot100.question17;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 热题100，#17，电话号码的字母组合：
 * 这道题最容易想到的就是用for循环，可是输入的字符串长度是不固定的，对应的循环的嵌套层数也是不固定的，那这种情况怎么解决呢？这时候递归就派上用场了。
 * 注意这里的思路，当for循环层数是动态的时候，要能够联想并尝试递归。
 * 所以方法一就是“递归”
 * 方法二：队列
 */
public class Solution {
    private List<String> res = new ArrayList<>();

    //数字字母映射表
    private String[] numMap = new String[]{" ", "!@#", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    /**
     * 方法一：递归
     * 由于输入数字字符串的长度不确定，导致for循环层数不确定，但是很容易"感觉"到，每层for循环的处理是“类似”的，因此可以尝试递归
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        //参数检测
        if (null == digits || digits.length() == 0) {
            return null;
        }

        //charAt比较耗时，转成数组，加快随机访问
        char[] digitArr = digits.toCharArray();

        //注意一个小技巧，用一个""做初始值，这种思想可以用来解决很多边界问题。
        recurFunc(digitArr, "", 0);

        return res;
    }

    /**
     * 递归函数
     * 根据题解中的递归树(图片在leetCode上)，可以思考出来（将根的"2"换成""空字符，然后去掉每一层的数字,这样就直观了），
     * 根节点从""开始，每个节点都是(该数字)对应一个字符，从根到叶的一条路径就是一个结果
     * 所以递归函数需要三个变量：
     * 1.每一个节点都是一个字符，所以需要一个index从输入digits中找出这个字符来
     * 2.为了满足1，digits自然也是一个输入参数，否则的话就要把它设置成静态变量
     * 3.从根到叶的路径就是一个结果，所以需要一个变量trackRes来追踪这个路径值，它的初始值就是根""
     */
    private void recurFunc(char[] digitArr, String trackRes, int index) {

        //递归返回条件，当越界的时候说明所有数字都遍历完了，可以返回trackRes了
        if (index == digitArr.length) {
            res.add(trackRes);
            return;
        }

        char num = digitArr[index];
        //注意这里减'0'得到偏移量的技巧
        char[] letterChars = numMap[num - '0'].toCharArray();

        for (int i = 0; i < letterChars.length; i++) {
            String curNodeRes = trackRes + letterChars[i];
            recurFunc(digitArr, curNodeRes, index + 1);
        }
    }

    /**
     * 方法二： 队列
     * @param digits
     * @return
     */
    public List<String> letterCombinations2(String digits) {
        //参数检测
        if (null == digits || digits.length() == 0) {
            return null;
        }

        //charAt比较耗时，转成数组，加快随机访问
        char[] digitArr = digits.toCharArray();

        //小技巧: 用""做起始值初始化
        Queue<String> queue = new LinkedList<>();
        //队列用""初始化的好处就是，第一轮可以看作是""与第一个数字对应字母的组合，这样就可以保持代码处理的一致性。
        queue.offer("");

        for (int i = 0; i < digitArr.length; i++) {
            char[] letterChars = numMap[digitArr[i] - '0'].toCharArray();
            int curQueueLen = queue.size();
            for (int j = 0; j < curQueueLen; j++) {
                //出队列
                String curRes = queue.poll();
                //组合后重入队列
                for (int k = 0; k < letterChars.length; k++) {
                    queue.offer(curRes + letterChars[k]);
                }
            }
        }

        return (LinkedList<String>) queue;
    }

    public static void main(String[] args) {
        String num = "234";
        Solution solution = new Solution();
        System.out.println(solution.letterCombinations(num));
        System.out.println(solution.letterCombinations2(num));
    }
}
