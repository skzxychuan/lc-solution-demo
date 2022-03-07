package hot100.question141;

/**
 * #141 环形链表
 */
public class Solution {

    /**
     * 方法一（自解）、双指针
     *
     * 定义两个指针，一个每次走一步（慢指针），另一个每次走两步（快指针）
     * 如果链表没有环，那么慢指针永远赶不上快指针
     * 如果链表有环，那么快指针迟早和慢指针相遇(丢圈),即对象相等
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {

        if (null == head || null == head.next) {
            return false;
        }

        ListNode p1 = head, p2 = head;

        while (true) {
            if (null != p1) {
                p1 = p1.next;
            }
            if (null != p2) {
                p2 = p2.next;
            }
            if (null != p2) {
                p2 = p2.next;
            }
            if (null == p1 && null == p2) {
                return false;
            }

            // 相遇的时候对象相等,说明有环
            // 这里不能写成p2.equals(p1)，否则会NPE
            if (p1.equals(p2)) {
                return true;
            }
        }
    }
}
