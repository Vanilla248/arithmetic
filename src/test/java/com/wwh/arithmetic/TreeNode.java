package com.wwh.arithmetic;

import java.util.*;

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
    TreeNode commonAncestor;
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        f(root, p, q);
        return commonAncestor;
    }
    private boolean f(TreeNode x, TreeNode p, TreeNode q){

        if(x == null){
            return false;
        }

        boolean lson = f(x.left, p, q);
        boolean rson = f(x.right, p, q);

        if((lson && rson) || ((x==p||x==q) && (lson || rson))){
            commonAncestor = x;
        }

        return (lson || rson) || x == p || x == q;
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

    // 从前序与中序遍历序列构造二叉树
    Map<Integer,Integer> inorderNums = new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int size = preorder.length;
        for(int i =0;i<size;i++){
            inorderNums.put(inorder[i],i);
        }
        return recursiveBuildTree(preorder, 0,size-1,0,size-1);
    }
    // 根据两个数组的范围递归构造树
    private TreeNode recursiveBuildTree(int[] preorder, int preorderLeft, int preorderRight, int inorderLeft, int inorderRight){

        if(preorderLeft > preorderRight){
            return null;
        }

        int rootNum = preorder[preorderLeft];
        TreeNode root = new TreeNode(rootNum);
        // 左右子树节点数量
        int leftSize = inorderNums.get(rootNum) - inorderLeft;
        root.left =  recursiveBuildTree(preorder, preorderLeft + 1, preorderLeft + leftSize, inorderLeft, inorderLeft + leftSize - 1);
        root.right = recursiveBuildTree(preorder, preorderLeft + leftSize + 1, preorderRight, inorderLeft + leftSize + 1,inorderRight);

        return root;
    }

    // 路径总和 III
    int ans = 0;
    long target;
    public int pathSumMe(TreeNode root, int targetSum) {
        target = targetSum;
        if(root == null){
            return 0;
        }
        preorder(root);
        if(root.val == targetSum){
            ans++;
        }
        return ans;
    }
    List<Integer> path = new ArrayList<>();
    private void preorder(TreeNode root){
        path.add(root.val);
        if(root.left != null){
            preorder(root.left);
            calPath();
            path.removeLast();
        }
        if(root.right != null){
            preorder(root.right);
            calPath();
            path.removeLast();
        }
    }
    private void calPath(){
        long sum = 0;
        for(int i=path.size()-1;i>=0;i--){
            sum += path.get(i);
            if(sum == target){
                ans++;
            }
        }
    }
    // 路径总和 III 前缀和
    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> prefix = new HashMap<>();
        prefix.put(0L, 1);
        return dfs(root, prefix, 0, targetSum);
    }

    public int dfs(TreeNode root, Map<Long, Integer> prefix, long curr, int targetSum) {
        if (root == null) {
            return 0;
        }

        int ret;
        curr += root.val;

        ret = prefix.getOrDefault(curr - targetSum, 0);
        prefix.put(curr, prefix.getOrDefault(curr, 0) + 1);
        ret += dfs(root.left, prefix, curr, targetSum);
        ret += dfs(root.right, prefix, curr, targetSum);
        prefix.put(curr, prefix.getOrDefault(curr, 0) - 1);

        return ret;
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