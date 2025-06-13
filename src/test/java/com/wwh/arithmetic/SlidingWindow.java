package com.wwh.arithmetic;

import java.util.Set;

public class SlidingWindow {
    
}
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new java.util.HashSet<>();
        int left = 0, right = 0, maxLength = 0;
        while (right < s.length()) {
            if (!set.contains(s.charAt(right))) {
                set.add(s.charAt(right));
                maxLength = Math.max(maxLength, right - left + 1);
                right++;
            } else {
                set.remove(s.charAt(left));
                left++;
            }
        }
        return maxLength;
    }
}