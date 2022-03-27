package hot100.question148;

/**
 * #148 排序链表
 * 进阶：你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
 *
 * TODO 耐心看题解吧，比较复杂，归并排序的两种方式：1.自顶向下(递归分治)。2.自底向上(迭代)。
 * TODO 两种方法看了很久才理清过程，特别是方法2，且只有方法2满足常数空间复杂度的要求。
 *
 * TODO 总结：要求时间是O(nlogn)，满足的排序只有堆排序、归并排序、快排。
 * TODO 快排最坏情况下会去到n^2时间复杂度，其中归并排序可以做到常数空间。
 */
public class Solution {

    /**
     * 归并排序、自顶向下递归
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {

        if (null == head || null == head.next) {
            return head;
        }

        // 通过双指针找中点，快指针一次2步，慢指针一次1步
        // 快指针不能再满足走两步的时候，慢指针要么在中间(链表为奇数)，要么在中间偏左(链表为偶数)
        ListNode slow = head;
        ListNode fast = head;
        while (null != fast.next && null != fast.next.next) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 切分为两个链表
        ListNode left = head;
        ListNode right = slow.next;
        slow.next = null;

        // 递归
        ListNode leftHead = sortList(left);
        ListNode rightHead = sortList(right);

        // 合并两个有序链表为一个有序链表
        ListNode mergeHead = merge(leftHead, rightHead);

        return mergeHead;
    }

    // 合并两个有序链表为一个有序链表
    private ListNode merge(ListNode leftHead, ListNode rightHead) {
        // 先准备一个伪链表头
        ListNode fakeHead = new ListNode(0);
        // 一个临时指针指向合并链表的最后一个元素
        ListNode tempTail = fakeHead;
        // 两个子链表的临时指针
        ListNode tempLeft = leftHead, tempRight = rightHead;
        // 由于两个子链表可能不一样长，左链表可能会比右链表长，
        // 因为奇数链表切分的时候slow指向中间，而中间节点被判给了左边,
        // 所以先排序合并都不为空的，再单独添加剩下的
        for (;null != tempLeft && null != tempRight;) {
            if (tempLeft.val < tempRight.val) {
                tempTail.next = tempLeft;
                tempLeft = tempLeft.next;
            } else {
                tempTail.next = tempRight;
                tempRight = tempRight.next;
            }
            tempTail = tempTail.next;
        }
        // 剩下的
        tempTail.next = null == tempLeft ? tempRight : tempLeft;
        return fakeHead.next;
    }

    /**
     * 归并排序、自底向上迭代
     * 第一轮 一一排序合并为二
     * 第二轮 两两排序合并为四
     * 第三轮 四四排序合并为八
     * 。。。。
     * 当当前要合并的子链表的长度大于等于总链表长度时，合并终止
     * @param head
     * @return
     */
    public ListNode sortList2(ListNode head) {
        if (null == head || null == head.next) {
            return head;
        }

        // 求链表长
        ListNode temp = head;
        int length = 0;
        while (null != temp) {
            length++;
            temp = temp.next;
        }

        // 构造一个总的伪头结点，方便统一操作
        ListNode fakeNode = new ListNode(0, head);

        // 每轮合并的子链表长度
        for (int subLen = 1; subLen < length; subLen = subLen << 1) {
            // 上一轮合并后的链表的最后一个节点，同时也是本轮合并后的链表的伪头结点，
            // 由于初始时没有上一轮，那就是整个链表的伪头结点。
            ListNode lastNode = fakeNode;
            // 当前指针，用于遍历，从当前轮的第一个元素开始
            ListNode pCur = lastNode.next;

            // 开始数两个链表出来进行合并
            while (null != pCur) {
                // 开始数本轮合并的第一个链表(从第一个点开始，往后数subLen-1个节点 或 到达末尾)
                // 数完之后pCur指向第一个链表的最后一个节点
                ListNode leftHead = pCur;
                for (int i = 0; i < subLen - 1 && pCur.next != null; i++) {
                    pCur = pCur.next;
                }

                // 本轮合并的第二个链表
                ListNode rightHead = pCur.next;
                // 断开第一个链表和第二个链表
                pCur.next = null;
                // 开始数本轮合并的第二个链表(从第一个点开始，往后数subLen-1个节点 或 到达末尾)
                // 数完之后pCur指向第二个链表的最后一个节点
                pCur = rightHead;
                // 这里注意加pCur != null这个条件
                for (int i = 0; i < subLen - 1 && pCur != null && pCur.next != null; i++) {
                    pCur = pCur.next;
                }

                // 数完了两个链表，先断开后面还没有数的
                ListNode remainingHead = null;
                // TODO 这里的判空很容易疏忽，比如一共三个元素的链表，第二轮一一合并为二时，pCur可能是空的
                if (null != pCur) {
                    remainingHead = pCur.next;
                    // 断开
                    pCur.next = null;
                }

                // 合并两个链表
                ListNode mergeHead = merge(leftHead, rightHead);

                // 将lastNode移动到本轮合并后的链表的最后一个节点，用作下一轮的伪头结点。
                lastNode.next = mergeHead;
                while (null != lastNode.next) {
                    lastNode = lastNode.next;
                }
                // 将pCur指向下一轮的第一个元素
                pCur = remainingHead;
            }
        }

        return fakeNode.next;
    }
}
