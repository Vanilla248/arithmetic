package com.wwh.arithmetic;

import java.util.Scanner;
import java.util.Stack;

class Solution {
    class Day{
        int index;
        int temperature;
        Day(int index, int temperature) {
            this.index = index;
            this.temperature = temperature;
        }
    }
    public int[] dailyTemperatures(int[] temperatures) {
        //单调栈
        Stack<Day> stack = new Stack<>();
        int len = temperatures.length;
        stack.push(new Day(0, temperatures[0]));
        //结果数组
        int[] result = new int[len];
        for(int i=1;i<len;i++){
            Day currentDay = new Day(i, temperatures[i]);
            while (!stack.isEmpty() && currentDay.temperature > stack.peek().temperature ) {
                Day previousDay = stack.pop();
                result[previousDay.index] = currentDay.index - previousDay.index;
            }
            stack.push(currentDay);
        }
        return result;
    }
}
public class Stacks {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        Solution solution = new Solution();
        int[] temperatures = {30, 40, 50, 60};
        int[] result = solution.dailyTemperatures(temperatures);
        for (int temp : result) {
            System.out.print(temp + " ");
        }
    }
}
