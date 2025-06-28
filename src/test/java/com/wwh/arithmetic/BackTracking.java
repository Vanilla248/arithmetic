package com.wwh.arithmetic;
import java.util.*;

public class BackTracking {
    
}
class Solution1 {
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
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        //给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        int n = nums.length;
        for (int num : nums) {
            current.add(num); // 初始化当前排列
        }
        backtrack(current, result, n, 0); // 开始回溯
        return result;
    }
    private void backtrack(List<Integer> current,List<List<Integer>> result,int n,int start){
        if (start==n) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < n; i++) {
            Collections.swap(current, i, start);
            backtrack(current, result, n, start + 1);
            Collections.swap(current, i, start); // 撤销交换
        }
    }
}