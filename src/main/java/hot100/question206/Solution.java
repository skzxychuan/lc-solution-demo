package hot100.question206;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * #206 反转链表
 * 进阶：链表可以选用迭代或递归方式完成反转。你能否用两种方法解决这道题？
 */
public class Solution {

    /**
     * 自解：用一个栈实现反转
     * TODO 效率还可以，但是不符合进阶
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (null == head) {
            return null;
        }

        Deque<ListNode> stack = new ArrayDeque();
        ListNode p = head;
        while(null != p) {
            stack.push(p);
            p = p.next;
        }

        ListNode newHead = stack.pop();
        p = newHead;
        while (!stack.isEmpty()) {
            ListNode node = stack.pop();
            p.next = node;
            // 注意这一句很容易疏忽，不加的话，会在弹出最后一个节点的时候产生环
            node.next = null;
            p = p.next;
        }

        return newHead;
    }



}
