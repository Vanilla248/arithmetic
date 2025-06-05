package com.wwh.arithmetic;
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