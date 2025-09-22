package com.wwh.arithmetic;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class Stacks {
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
        Deque<Day> stack = new LinkedList<>();
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

    public String decodeString(String s) {
        Deque<Integer> countStack = new LinkedList<>();
        Deque<StringBuilder> stringStack = new LinkedList<>();
        StringBuilder currentString = new StringBuilder();
        int count = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                count = count * 10 + (c - '0');
            }else if (c == '[') {
                countStack.push(count);
                stringStack.push(currentString);
                currentString = new StringBuilder();
                count = 0;
            }else if (c == ']') {
                StringBuilder previousStringBuilder = stringStack.pop();
                int repeatTimes = countStack.pop();
                for (int i = 0; i < repeatTimes; i++) {
                    previousStringBuilder.append(currentString);
                }
                currentString = previousStringBuilder;
            }else {
                currentString.append(c);
            }
        }
        return currentString.toString();
    }
}