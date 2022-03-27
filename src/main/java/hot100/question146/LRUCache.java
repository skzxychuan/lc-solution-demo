package hot100.question146;

import java.util.HashMap;

/**
 * #146 LRU缓存
 * 很高频的一套题，相关LinkedHashMap源码
 */
public class LRUCache {

    // 核心就是一个双向链表和一个哈希表
    private Node head;
    private Node tail;
    private HashMap<Integer, Node> map;

    // 当前大小
    private Integer size;
    // 最大容量capacity
    private Integer capacity;

    public LRUCache(int capacity) {
        // 构造链表双向链表，不存储值的头节点和尾结点，方便统一之后的操作
        this.head = new Node();
        this.tail = new Node();
        this.head.next = this.tail;
        this.tail.pre = this.head;

        // 哈希表
        this.map = new HashMap<>();

        this.capacity = capacity;
        // 初始大小为0
        this.size = 0;
    }

    public int get(int key) {
        // 先从哈希表中找，哈希表中不存在，那就返回-1,
        // 如果存在，将链表中的对应节点放在前面(最近访问的)，再从哈希表中返回值
        Node node = map.get(key);
        if (null == node) {
            return -1;
        }
        moveNode2Head(node);
        return node.value;
    }

    public void put(int key, int value) {
        // 插入操作，如果哈希表中不存在，则直接插入哈希表，插入链表头，判断是否需要缓存逐出
        // 如果哈希表中存在，则更新哈希表的值，更新并调整对应节点到链表头
        Node node = map.get(key);
        if (null != node) {
            node.value = value;
            moveNode2Head(node);
            return;
        }
        node = new Node(key, value);
        insertNode2Head(node);
        map.put(key, node);
        size++;
        // 当前大小超过了最大容量，逐出链表尾的缓存，并删除哈希表中对应对象
        if (size > capacity) {
            map.remove(tail.pre.key);
            tail.pre.pre.next = tail;
            tail.pre = tail.pre.pre;
        }
    }

    // 插入一个新节点到链表头
    private void insertNode2Head(Node node) {
        head.next.pre = node;
        node.next = head.next;
        head.next = node;
        node.pre = head;
    }

    // 将节点移动到链表头
    private void moveNode2Head(Node node) {
        // 删除当前节点
        node.pre.next = node.next;
        node.next.pre = node.pre;
        // 重新将当前节点插到表头
        head.next.pre = node;
        node.next = head.next;
        head.next = node;
        node.pre = head;
    }

    // 内部对象
    class Node {
        private int key;
        private int value;
        private Node pre;
        private Node next;

        public Node() { }

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // 打印链表
    public void printLink() {
        Node p = head.next;
        while (null != p && p != tail) {
            System.out.print("[" + p.key + "," + p.value + "] -> ");
            p = p.next;
        }
        System.out.println("\r\n");
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1,1);
        cache.printLink();
        cache.put(2,2);
        cache.printLink();
        cache.get(1);
        cache.printLink();
        cache.put(3, 3);
        cache.printLink();
        cache.get(2);
        cache.printLink();
        cache.put(4, 4);
        cache.printLink();
        cache.get(1);
        cache.printLink();
        cache.get(3);
        cache.printLink();
        cache.get(4);
    }
}
