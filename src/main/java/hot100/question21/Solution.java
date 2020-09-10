package hot100.question21;

/**
 * 热题100，#21，合并两个有序链表：
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * 方法一：自己想的暴力解
 * 方法二：递归
 */
public class Solution {

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 方法一：自己想的暴力解
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (null == l1 && null == l2) {
            return null;
        }
        if (null == l1 && null != l2) {
            return l2;
        }
        if (null != l1 && null == l2) {
            return l1;
        }

        //构造头节点和当前指针
        ListNode head1 = new ListNode(-1, l1);
        ListNode head2 = new ListNode(-1, l2);
        ListNode p1 = head1.next;
        ListNode p2 = head2.next;

        //其实就是将一个链表的元素插入另一个链表
        while (null != p1) {
            //如果要插入的元素小于等于第二个链表的所有元素，头插
            if (p1.val <= head2.next.val) {
                ListNode newFirstNode = new ListNode(p1.val, head2.next);
                head2.next = newFirstNode;
            } else {
                //双指针，方便中间插值
                ListNode q = head2;
                while (null != p2) {
                    //中间插
                    if (p2.val >= p1.val) {
                        ListNode insertNode = new ListNode(p1.val, p2);
                        q.next = insertNode;
                        break;
                    }
                    //注意这里的判断也容易被忽视，即插入元素比第二个链表所有元素都大，尾插
                    if (null == p2.next) {
                        ListNode tailNode = new ListNode(p1.val, null);
                        p2.next = tailNode;
                        break;
                    }
                    p2 = p2.next;
                    q = q.next;
                }
            }
            p1 = p1.next;
            //注意这里p2要复位，很容易忽视
            p2 = head2.next;
        }

        return head2.next;
    }
}
