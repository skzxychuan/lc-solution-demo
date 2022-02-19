package hot100.question48;

import java.util.LinkedList;

public class Solution {

    /**
     * #48 旋转图像
     * 从外圈转到内圈，反过来也可以
     * 单看每一圈，都相当于将此圈的数字向后移动 n-1 位，n为当前圈的边长
     * @param matrix
     */
    public void rotate(int[][] matrix) {

        if (matrix.length == 1) {
            return;
        }

        // 用一个队列来做移动
        LinkedList<Integer> queue = new LinkedList<>();

        // (0,0)~(0,n-1)
        // (1,n-1)~(n-1,n-1)
        // (n-1,n-2)~(n-1,0)
        // (n-2,0)~(1,0)
        // 以左上角为原点，上述为某圈队列的坐标的变化规律：
        // y坐标增到最大 -> x坐标增到最大 -> y坐标减到0 -> x坐标减到1(是1不是0)
        // 需要转的圈数为 matrix.length / 2
        for (int n = 1; n <= matrix.length / 2; n++) {
            // 每转一圈长度减2，第一圈不用减
            int len = matrix.length - (n - 1) * 2;
            // 按圈入队列，注意每圈的起点终点会变化
            int xStart = n - 1;
            int xEnd = matrix.length - n;
            int yStart = n - 1;
            int yEnd = matrix.length - n;
            for (int y = yStart; y <= yEnd; y++) {
                queue.add(matrix[xStart][y]);
            }
            for (int x = xStart + 1; x <= xEnd; x++) {
                queue.add(matrix[x][yEnd]);
            }
            for (int y = yEnd - 1; y >= yStart; y--) {
                queue.add(matrix[xEnd][y]);
            }
            for (int x = xEnd - 1; x >= xStart + 1; x--) {
                queue.add(matrix[x][yStart]);
            }
            System.out.println(queue.toString());

            // 每圈旋转90度，等效于将此圈最后len-1个数移动到此圈队列的前面
            for (int i = 0; i < len - 1; i++) {
                queue.offerFirst(queue.removeLast());
            }
            System.out.println(queue.toString());
            System.out.println("-------------------------");

            // 将旋转后的队列重新填入
            for (int y = yStart; y <= yEnd; y++) {
                matrix[xStart][y] = queue.poll();
            }
            for (int x = xStart + 1; x <= xEnd; x++) {
                matrix[x][yEnd] = queue.poll();
            }
            for (int y = yEnd - 1; y >= yStart; y--) {
                matrix[xEnd][y] = queue.poll();
            }
            for (int x = xEnd - 1; x >= xStart + 1; x--) {
                matrix[x][yStart] = queue.poll();
            }
            queue.clear();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{2,29,20,26,16,28},{12,27,9,25,13,21},{32,33,32,2,28,14},{13,14,32,27,22,26},{33,1,20,7,21,7},{4,24,1,6,32,34}};
        Solution solution = new Solution();
        solution.rotate(matrix);
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix.length; y++) {
                System.out.print(matrix[x][y]);
            }
        }
    }
}
