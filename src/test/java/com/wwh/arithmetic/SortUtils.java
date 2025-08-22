package com.wwh.arithmetic;

import java.util.*;

public class SortUtils {
    
}
class Solution1 {
    /**
     * 找出数组中出现频率前 k 高的元素
     * @param nums 整数数组
     * @param k    前 k 个高频元素
     * @return 包含前 k 个高频元素的数组
     */
    public int[] topKFrequent(int[] nums, int k) {
        // 步骤1: 统计每个元素的出现频率
        // 使用 HashMap<元素, 频率> 来存储
        Map<Integer,Integer> frequencyMap = new HashMap<>();
        for(int num : nums){
            frequencyMap.put(num,frequencyMap.getOrDefault(num, 0)+1);
        }

        // 步骤2: 使用优先队列（最小堆）来找到频率最高的 k 个元素
        // PriorityQueue 默认是最小堆，我们自定义比较器，按 Map.Entry 的 value (频率) 升序排列
        // 这样堆顶始终是当前已遍历元素中频率最小的
        PriorityQueue<Map.Entry<Integer,Integer>> queue = new PriorityQueue<>(k+1, (a,b) -> a.getValue()-b.getValue());
        
        // 遍历频率 Map
        for(Map.Entry<Integer, Integer> entry: frequencyMap.entrySet()){
            // 将键值对（元素-频率）加入队列
            queue.offer(entry);
            // 如果队列的大小超过了 k，就将堆顶元素（频率最小的）移除
            // 这样可以确保队列中始终维护着当前已遍历元素的 top k 高频元素
            if (queue.size()>k) {
                queue.poll();
            }
        }

        // 步骤3: 从优先队列中提取结果
        // 循环结束后，队列中剩下的就是整个数组中频率最高的 k 个元素
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            // 从队列中取出元素，并获取其键（即原始数字）
            result[i] = queue.poll().getKey();
        }
        return result;
    }
}

class Solution2 {
    public int searchInsert(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid; // 找到目标值，返回索引
            } else if (nums[mid] < target) {
                start = mid + 1; // 目标值在右侧，移动左边
            } else {
                end = mid - 1; // 目标值在左侧，移动右边
            }
        }
        // 如果未找到目标值，返回插入位置
        return start; // 此时 start 即为目标值应插入的位置
    }
}

class Solution3 {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0) return false; // 如果矩阵为空，直接返回
        int n = matrix[0].length;
        int left = 0, right = m * n - 1; // 将二维矩阵视为一维数组
        while (left <= right) {
            int mid = left + (right - left) / 2; // 防止整数溢出
            int midValue = matrix[mid / n][mid % n]; // 计算中间元素的值
            if (midValue == target) {
                return true; // 找到目标值，返回 true
            } else if (midValue < target) {
                left = mid + 1; // 目标值在右侧，移动左边
            } else {
                right = mid - 1; // 目标值在左侧，移动右边
            }
        }
        return false; // 如果未找到目标值，返回 false
    }
}

class Solution4 {
    public int[] searchRange(int[] nums, int target) {
        // 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。

        // 如果数组中不存在目标值 target，返回 [-1, -1]。

        // 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
        int[] result = {-1, -1};
        int left = 0, right = nums.length - 1;
        // 查找左边界
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1; // 目标值在右侧，移动左边
            } else {
                right = mid - 1; // 目标值在左侧，移动右边
            }
        }
        if (left < nums.length && nums[left] == target) {
            result[0] = left; // 找到左边界
        }
        // 查找右边界
        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1; // 目标值在右侧或等于中间值，移动左边
            } else {
                right = mid - 1; // 目标值在左侧，移动右边
            }
        }
        if (right >= 0 && nums[right] == target) {
            result[1] = right; // 找到右边界
        }
        return result; // 返回结果数组
    }
}

class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid; // 找到目标值，返回索引
            }
            // 判断当前区间是否有序
            if (nums[left] <= nums[mid]) { // 左半部分有序
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1; // 目标值在左半部分
                } else {
                    left = mid + 1; // 目标值在右半部分
                }
            } else { // 右半部分有序
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1; // 目标值在右半部分
                } else {
                    right = mid - 1; // 目标值在左半部分
                }
            }
        }
        return -1; // 如果未找到目标值，返回 -1
    }
}