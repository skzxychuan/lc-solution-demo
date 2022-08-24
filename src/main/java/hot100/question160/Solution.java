package hot100.question160;

import java.util.ArrayList;
import java.util.List;

/**
 * #160 相交列表
 * 进阶：你能否设计一个时间复杂度 O(m + n) 、仅用 O(1) 内存的解决方案？
 */
public class Solution {
    /**
     * 自解，先遍历第一个链表，记录所有对象的内存地址，再遍历第二个链表，第一个内存地址重复的点就是第一个交点
     * TODO 能通过，但是不符合进阶条件，而且效率很低，只击败5.11%用户
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (null == headA || null == headB) {
            return null;
        }
        if (headA.hashCode() == headB.hashCode()) {
            return headA;
        }

        // 记录第一个链表的所有内存地址
        ListNode p = headA;
        List<Integer> memAddrs = new ArrayList<>();
        while (null != p) {
            memAddrs.add(p.hashCode());
            p = p.next;
        }

        // 遍历第二个链表，查看内存地址是否已存在
        p = headB;
        while (null != p) {
            if (memAddrs.contains(p.hashCode())) {
                return p;
            }
            p = p.next;
        }
        return null;
    }

    /**
     * 官解方法二：双指针
     * 具体思路见官解题解。
     * 总之就是通过两个指针相互遍历，来消除长度差(总步数一样)
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (null == headA || null == headB) {
            return null;
        }

        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = null == pA ? headB : pA.next;
            pB = null == pB ? headA : pB.next;
        }
        return pA;
    }
}
