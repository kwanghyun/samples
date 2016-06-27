package algorithm.trees;

import java.util.Stack;

/*
 * Given a binary search tree, write a function kthSmallest to find the kth
 * smallest element in it. (1 ≤ k ≤ BST's total elements)
 */
public class KthSmallestElementInBST {
	public int kthSmallest(TreeNode root, int k) {
		Stack<TreeNode> stack = new Stack<TreeNode>();

		TreeNode p = root;
		int result = 0;

		while (!stack.isEmpty() || p != null) {
			if (p != null) {
				stack.push(p);
				p = p.left;
			} else {
				TreeNode t = stack.pop();
				k--;
				if (k == 0)
					result = t.value;
				p = t.right;
			}
		}

		return result;
	}
}
