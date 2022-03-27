package hot100.question155;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * #155 最小栈
 * TODO 官解：辅助栈
 * TODO 进阶：可以不适用额外空间解决么？（见官解下面的评论，不想写了）
 */
public class MinStack {

    // 主栈
    private Deque<Integer> dataStack;
    // 辅助栈：保存实时最小值，每入主栈一个元素，就把这个元素对应的最小值入栈顶(先和之前的栈顶比较)
    private Deque<Integer> minStack;

    public MinStack() {
        dataStack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
        // 每个元素入辅助栈，都要先和之前的辅助栈顶(也就是最小值)比较，
        // 所以把辅助栈先初始化，统一后续的操作（要习惯并且能反射性的想到这种技巧）
        minStack.push(Integer.MAX_VALUE);
    }

    public void push(int val) {
        // 入主栈
        dataStack.push(val);
        // 入辅助栈
        minStack.push(Math.min(minStack.peek(), val));
    }

    public void pop() {
        dataStack.pop();
        minStack.pop();
    }

    public int top() {
        return dataStack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }

}
