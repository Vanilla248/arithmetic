package com.wwh.arithmetic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
class TreeNodeSolution {
    //寻找最二叉树最近公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root==p||root==q||root==null) {
            return root;
        }
        // 递归查找左子树和右子树
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        // 如果左子树和右子树都找到了目标节点，说明当前节点就是最近公共祖先
        if (left != null && right != null) {
            return root;
        }
        // 否则返回非空的那个子树的结果
        return left != null? left : right;
    }
    //二叉树的右视图
    public List<Integer> rightSideView(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        //层序遍历
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode currentNode = queue.poll();
                // 如果当前节点有左子节点，先加入左子节点
                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                // 如果当前节点有右子节点，加入右子节点
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
                // 如果是当前层的最后一个节点，加入结果集
                if (i == size - 1) {
                    result.add(currentNode.val);
                }
            }
        }
        return result;
    }
    //将二叉树展开为链表
    public void flatten(TreeNode root) {
        //展开后的单链表应该与二叉树 先序遍历 顺序相同。
        //展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null。
        if (root == null) {
            return;
        }
        // 先序遍历
        flatten(root.left);
        flatten(root.right);
        if (root.left!=null) {
            TreeNode rightSubtree = root.right; // 保存右子树
            root.right = root.left; // 将左子树连接到右子树
            root.left = null; // 将左子树置为 null
            // 找到新的右子树的末尾
            TreeNode current = root.right;
            while (current.right != null) {
                current = current.right;
            }
            // 将原来的右子树连接到新的右子树末尾
            current.right = rightSubtree;
        }
    }
}
// 字典树
class Trie {
    private Trie[] children;
    private boolean isEnd;

    public Trie() {
        children = new Trie[26];
        isEnd = false;
    }

    public void insert(String word) {
        Trie node = this;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new Trie();
                node = node.children[index];
            } else {
                node = node.children[index];
            }
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        Trie node = this;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                return false;
            } else {
                node = node.children[index];
            }
        }
        return node.isEnd;
    }

    public boolean startsWith(String prefix) {
        Trie node = this;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                return false;
            } else {
                node = node.children[index];
            }
        }
        return true;
    }
}