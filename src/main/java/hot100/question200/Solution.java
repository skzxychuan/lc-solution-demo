package hot100.question200;

/**
 * #200 岛屿数量
 */
public class Solution {

    /**
     * 解法一、DFS 深度优先
     */
    public int numIslands(char[][] grid) {
        int cnt = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                // 如果是没有遍历过的陆地格子(1)，则开始深度优先遍历
                if (grid[r][c] == '1') {
                    dfs(grid, grid.length, grid[0].length, r, c);
                    // 一次遍历之后，一块陆地的所有格子(1)就变成了2，不会重复遍历,所以遇到的1的数量就是岛屿数
                    cnt++;
                }
            }
        }

        return cnt;
    }

    private void dfs(char[][] grid, int m, int n, int r, int c) {
        // 超出边界则返回，不需要再遍历
        if (!inArea(m, n, r, c)) {
            return;
        }

        // 到了非陆地，不需要再遍历
        if (grid[r][c] == '0') {
            return;
        }

        // 当前点已经遍历过，不需要再遍历
        if (grid[r][c] == '2') {
            return;
        }

        // 没有遍历过，则遍历当前点，把1改成2,表示已经遍历过
        grid[r][c] = '2';

        // 深度优先其他点，有四个方向
        dfs(grid, m, n, r - 1, c);
        dfs(grid, m, n, r + 1, c);
        dfs(grid, m, n, r, c - 1);
        dfs(grid, m, n, r, c + 1);
    }

    // 是否出界
    private boolean inArea(int m, int n, int r, int c) {
        return r >= 0 && r < m && c >=0 && c < n;
    }

}
