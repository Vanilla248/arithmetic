package com.wwh.arithmetic;

import java.util.*;

public class ListNode {
    int val;
    ListNode next;
    ListNode random;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
class Solution1 {
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

class Solution2 {
    public ListNode copyRandomList(ListNode head) {
        Map<ListNode, ListNode> map = new HashMap<>();
        ListNode current = head;
        // 第一次遍历，复制节点并建立映射
        while (current != null) {
            map.put(current, new ListNode(current.val));
            current = current.next;
        }
        current = head;
        // 第二次遍历，设置 next 和 random 指针
        while (current != null) {
            ListNode newNode = map.get(current);
            newNode.next = map.get(current.next);
            newNode.random = map.get(current.random);
            current = current.next;
        }
        return map.get(head); // 返回新链表的头节点
    }
}

class Solution {
    public ListNode sortList(ListNode head) {
        return sortList(head,null);
    }
    public ListNode sortList(ListNode head, ListNode tail){
        if (head == null) {
            return head;
        }
        if (head.next == tail) {
            head.next = null;
            return head;
        }
        ListNode slow = head, fast = head;
        // 使用快慢指针找到中间节点
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next;
            if (fast.next != tail) {
                fast = fast.next;
            }
        }
        ListNode mid = slow;
        ListNode left = sortList(head, mid); // 对左半部分排序
        ListNode right = sortList(mid, tail); // 对右半部分排序
        return merge(left, right); // 合并两个已排序的链表
    }
    public ListNode merge(ListNode head1, ListNode head2){
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                current.next = head1;
                head1 = head1.next;
            } else {
                current.next = head2;
                head2 = head2.next;
            }
            current = current.next;
        }
        if (head1 != null) {
            current.next = head1;
        } else {
            current.next = head2;
        }
        return dummy.next; // 返回合并后的链表头节点
    }
}

class LRUCache {

    class DlinkedNode {
        int key;
        int value;
        DlinkedNode prev;
        DlinkedNode next;
        public DlinkedNode() {}
        public DlinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    int capacity;
    Map<Integer, DlinkedNode> cache = new HashMap<>();
    DlinkedNode head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head = new DlinkedNode();
        tail = new DlinkedNode();
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (cache.containsKey(key)) {
            DlinkedNode node = cache.get(key);
            removeNode(node);
            addToHead(node);
            return node.value;
        } else {
            return -1;
        }
    }
    
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            DlinkedNode node = cache.get(key);
            node.value = value;
            moveToHead(node);
        }else{
            DlinkedNode newNode = new DlinkedNode(key, value);
            cache.put(key, newNode);
            addToHead(newNode);
            if (cache.size() > capacity) {
                DlinkedNode tail = removeTail();
                cache.remove(tail.key);
            }
        }
    }

    private void addToHead(DlinkedNode node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
    private void moveToHead(DlinkedNode node) {
        removeNode(node);
        addToHead(node);
    }
    private void removeNode(DlinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    private DlinkedNode removeTail() {
        DlinkedNode node = tail.prev;
        removeNode(node);
        return node;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */