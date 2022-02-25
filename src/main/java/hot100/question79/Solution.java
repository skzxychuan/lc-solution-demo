package hot100.question79;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/** #79 单词搜索 */
public class Solution {

    /**
     * 自解 回溯算法
     * TODO 勉强通过，击败5%用户，一开始用的List<String> path来记录已经走过的坐标字符串，结果超时
     * TODO 后来将List<String> path换成int[][]visited，勉强通过，看来字符串比较是很费时的。
     * 1. 矩阵中所有可能的起点
     * 2. 对每个可能的起点做回溯(每一个点都可以有 上下左右四个方向可以走，边界和来源方向除外)
     * 3. 返回结果
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {

        List<Integer[]> starts = new ArrayList<>();

        //1.先遍历一次矩阵，找出所有起点
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    starts.add(new Integer[]{i,j});
                }
            }
        }

        // 没找到直接返回
        if (starts.size() == 0) {
            return false;
        }

        // 路径，用来标记走过的点
        // TODO 如果这里改成List<String> path 用坐标的字符串去判断是否走过，则提交答案超时。字符串比较很费时。
        int[][] visited = new int[board.length][board[0].length];

        //2. 对每一个可能的起点做回溯, 有一个起点找到了就返回
        for (Integer[] star : starts) {
            Boolean[] res = new Boolean[]{false};
            // 记录起点
            visited[star[0]][star[1]] = 1;
            dfs(board, word, 0, star[0], star[1], visited, res);
            visited[star[0]][star[1]] = 0;
            if (res[0]) {
                return true;
            }
        }

        return false;
    }

    private void dfs(char[][]board, String word, int idx, int row, int col, int[][] visited, Boolean[] res) {
        // 搜索完毕则返回
        //TODO 这里不能写成 idx == word.length()，否则不兼容输入 board[['a']] 和 word="a"
        if (idx == word.length() - 1 && board[row][col] == word.charAt(idx)) {
            res[0] = true;
            return;
        }
        // 剪枝，当前点的字符与要搜索的字符不相等，没必要继续
        if (board[row][col] != word.charAt(idx)) {
            return;
        }

        // 计算可能的下一个点，不能越界
        List<Integer[]> nexts = new LinkedList<>();
        // 不在row边界，可以往上下
        if (row != 0) {
            nexts.add(new Integer[]{row - 1, col});
        }
        if (row != board.length - 1) {
            nexts.add(new Integer[]{row + 1, col});
        }
        // 不在col边界，可以左右
        if (col != 0) {
            nexts.add(new Integer[]{row, col - 1});
        }
        if (col != board[0].length - 1) {
            nexts.add(new Integer[]{row, col + 1});
        }
        // 剪枝，剔除已经走过的坐标
        Iterator<Integer[]> iterator = nexts.iterator();
        while (iterator.hasNext()) {
            Integer[] next = iterator.next();
            if (visited[next[0]][next[1]] == 1) {
                iterator.remove();
            }
        }

        // 选择列表
        for (Integer[] next : nexts) {
            // 选择下一个点
            visited[next[0]][next[1]] = 1;
            // 从下一个点搜索下一个字符
            dfs(board, word, idx + 1, next[0], next[1], visited, res);
            // 撤销选择
            visited[next[0]][next[1]] = 0;
        }
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        String word = "ABCCED";
        System.out.println(new Solution().exist(board, word));
    }
}
