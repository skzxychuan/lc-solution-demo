package hot100.question208;

/**
 * #208 实现Trie(前缀树、字段数、单词查找树)
 */
public class Trie {

    private Node root;

    public class Node {
        public boolean isEnd = false;
        public Node[] nodeList = new Node[26];
    }

    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        Node curNode = root;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            if (null == curNode.nodeList[index]) {
                curNode.nodeList[index] = new Node();
            }
            curNode = curNode.nodeList[index];
        }
        curNode.isEnd = true;
    }

    public boolean search(String word) {
        char[] chars = word.toCharArray();
        Node curNode = root;
        for (char ch : chars) {
            int index = ch - 'a';
            if (curNode.nodeList[index] == null) {
                return false;
            }
            curNode = curNode.nodeList[index];
        }
        return curNode.isEnd;
    }

    public boolean startsWith(String prefix) {
        char[] chars = prefix.toCharArray();
        Node curNode = root;
        for (char ch : chars) {
            int index = ch - 'a';
            if (curNode.nodeList[index] == null) {
                return false;
            }
            curNode = curNode.nodeList[index];
        }
        return true;
    }
}
