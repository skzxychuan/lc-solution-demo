package hot100.question142;

import java.util.HashSet;
import java.util.Set;

/**
 * #142 环形链表2
 * 进阶：你是否可以使用 O(1) 空间解决此题？
 */
public class Solution {
    /**
     * 自解，哈希表
     * 遍历一次链表，遇到重复的元素则代表有环且环的入口就是该元素
     * TODO 能通过，但是只击败20%的用户，并且不满足进阶要求
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if (null == head) {
            return null;
        }

        Set<ListNode> exist = new HashSet<>();
        ListNode p = head;
        while(null != p) {
            if (exist.contains(p)) {
                return p;
            }
            exist.add(p);
            p = p.next;
        }
        return null;
    }

    /**
     * 精选解，双指针
     * 这类链表题目一般都是使用双指针法解决的，例如寻找距离尾部第K个节点、寻找环入口、寻找公共尾部入口等。
     * TODO 总之就是通过两次相遇来确定入口点，具体见精选题解：
     * 第一次是真的套圈相遇。
     * 第二次是重置fast指针之后，两指针都一次走一步，重新在环入口相遇，此时fast移动的步数就是入口节点的索引。
     * @param head
     * @return
     */
    public ListNode detectCycle2(ListNode head) {
        // 快慢指针同时起跑
        ListNode fast = head;
        ListNode slow = head;

        // 第一次相遇
        while(true) {
            // 快指针为null说明一定没有环
            if (null == fast || null == fast.next) {
                return null;
            }
            // 快指针走两步
            fast = fast.next.next;
            // 慢指针走一步
            slow = slow.next;
            // 首次相遇，退出，准备重置fast
            if (fast == slow) {
                break;
            }
        }

        // 重置快指针，每次只走一步，准备第二次相遇，重新相遇一定是入口
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }
}
