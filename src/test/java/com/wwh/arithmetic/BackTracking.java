package com.wwh.arithmetic;
import java.util.*;

public class BackTracking {
    
}
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        // 回溯算法，返回所有子集
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        backtrack(nums, 0, current, result);
        return result;
    }
    private void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        // 将当前子集加入结果集
        result.add(new ArrayList<>(current));
        // 遍历所有可能的子集
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]); // 选择当前元素
            backtrack(nums, i + 1, current, result); // 递归调用
            current.remove(current.size() - 1); // 撤销选择
        }
    }
}