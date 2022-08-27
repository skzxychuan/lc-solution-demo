package hot100.question207;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * #207 课程表
 */
public class Solution {

    /**
     * 根据最高赞题解思路写的答案
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {

        // 没有要学的课程 返回 true
        if (0 == prerequisites.length) {
            return true;
        }

        // 将数组转成（邻接表） key是数字，value是以key数字为终点的所有数字的列表，即起点列表
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int[] prerequisite : prerequisites) {
            int src = prerequisite[0];
            int dst = prerequisite[1];

            // 初始化出现的数字的起点列表
            if (null == map.get(dst)) {
                map.put(dst, new LinkedList<>());   // 终点数字的起点列表
            }
            if (null == map.get(src)) {
                map.put(src, new LinkedList<>());   // 起点数字的起点列表
            }

            // 记录以dst为终点的所有起点
            if (!map.get(dst).contains(src)) {
                map.get(dst).add(src);
            }
        }

        // 利用一个队列，第一批入度为0的点入队列（只能首先学习入读为0的点）
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            if (entry.getValue().size() == 0) {
                queue.offer(entry.getKey());
            }
        }

        int alreadylearn = 0;
        while (!queue.isEmpty()) {
            // 从队列中挑出一个入读为0的课程学习,学完后删除
            Integer num = queue.poll();
            map.remove(num);
            alreadylearn++;

            // 学过的课已经达到数量则返回
//            if (alreadylearn == numCourses) {
//                return true;
//            }

            // 学完后，所有以此课程为前置课程的点，都要删除此课程
            for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                entry.getValue().remove(num);
            }

            // 下一批入读为0的课程入队列
            for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                if (entry.getValue().size() == 0 && !queue.contains(entry.getKey())) {
                    queue.offer(entry.getKey());
                }
            }
        }

        // 所有能学的课(入读为0)都学完了，还没有满足要求，返回false;
//        return false;

        //TODO 妈的这题有歧义，并不是学了numCourses就返回。实际是把数组中的学完就是true了。
        return map.isEmpty();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.canFinish(5, new int[][]{
                new int[] {1, 4},
                new int[] {2, 4},
                new int[] {3, 1},
                new int[] {3, 2}
        });
    }
}
