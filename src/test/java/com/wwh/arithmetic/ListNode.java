package com.wwh.arithmetic;

public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
class Solution {
    public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) {
            return head;
        }
        ListNode firListNode = head;
        ListNode secListNode = head.next;
        ListNode ans = head.next;
        while (true) {
            // 交换两个节点
            firListNode.next = secListNode.next;
            secListNode.next = firListNode;
            // 更新指针
            if (firListNode.next == null || firListNode.next.next == null) {
                break;
            }
            ListNode nextFir = firListNode.next;
            ListNode nextSec = nextFir.next;
            firListNode.next = nextSec;
            firListNode = nextFir;
            secListNode = nextSec;
        }
        return ans;
    }
}