package algorithm.trees;

public class TreeNode {
	int value;
	String str;
	TreeNode left;
	TreeNode right;

	public TreeNode(int value) {
		this.left = null;
		this.right = null;
		this.value = value;
	}

	public TreeNode(String str) {
		this.left = null;
		this.right = null;
		this.str = str;
	}

	public TreeNode(TreeNode left, TreeNode right, int value) {
		this.left = left;
		this.right = right;
		this.value = value;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public TreeNode getRight() {
		return right;
	}

	public int getValue() {
		return value;
	}

	public void printValue() {
		System.out.println(value);

	}
}