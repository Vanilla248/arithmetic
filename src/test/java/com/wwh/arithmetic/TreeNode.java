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
//寻找最二叉树最近公共祖先
class Solution1 {
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
}
//二叉树的右视图
class Solution {
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
}