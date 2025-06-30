package com.wwh.arithmetic;
import java.util.*;

public class BackTracking {
    
}
//回溯函数的常见参数：方法的传参、开始下标、当前路径、结果集等。
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
class Solution2 {
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
//给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。

//给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result; // 如果输入为空，返回空列表
        }
        StringBuilder current = new StringBuilder(); // 用于存储当前组合
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtrack(digits, 0, current, result, mapping);
        return result; // 返回所有组合
    }
    public void backtrack(String digits, int index, StringBuilder current, List<String> result, String[] mappStrings){
        if ((index==digits.length())) {
            result.add(current.toString()); // 如果当前组合长度等于输入长度，添加到结果
            return;
        }
        int digit = digits.charAt(index) - '0'; // 获取当前数字
        String letters = mappStrings[digit]; // 获取对应的字母
        for(char letter : letters.toCharArray()){   
            current.append(letter);
            backtrack(digits, index + 1, current, result, mappStrings); // 递归调用
            current.deleteCharAt(index); // 撤销选择?
            
        }
    }
}