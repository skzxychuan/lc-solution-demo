package hot100.question49;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * #49 字母异位词分组
 * 排序 + 哈希表
 * 这种“归类”的问题，要很自然的想到哈希表 并适当的辅助排序
 */
public class Solution {

    public List<List<String>> groupAnagrams(String[] strs) {

        Map<Integer, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            int key = new String(chars).hashCode();
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }

        List<List<String>> res = new ArrayList<>();
        map.forEach((k, v) -> {
            List<String> group = new ArrayList<>();
            group.addAll(v);
            res.add(group);
        });

        return res;
    }
}
