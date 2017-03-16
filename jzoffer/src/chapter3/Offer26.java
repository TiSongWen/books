package chapter3;

/**
 * Created by tisong on 3/16/17.
 */
public class Offer26 {

    private static class BinaryTreeNode {
        int value;
        BinaryTreeNode left;
        BinaryTreeNode right;

        public BinaryTreeNode(int value, BinaryTreeNode left, BinaryTreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private static BinaryTreeNode findLeftMaxNode(BinaryTreeNode root) {
        if (root.right == null) {
            return root;
        }

        return findLeftMaxNode(root.right);
    }

    private static BinaryTreeNode findRightMinNode(BinaryTreeNode root) {
        if (root.left == null) {
            return root;
        }

        return findRightMinNode(root.left);
    }

    public static BinaryTreeNode convertLinkedList(BinaryTreeNode root) {

        return null;
    }

    private static BinaryTreeNode convertLinkedList(BinaryTreeNode root, boolean left) {
        if (root == null) {
            return null;
        }

        BinaryTreeNode leftTree = convertLinkedList(root.left, true);
        BinaryTreeNode rightTree = convertLinkedList(root.right, false);

        if (leftTree == null && rightTree == null) {
            return root;
        }
        else if (leftTree != null && rightTree == null) {
            leftTree.right = root;
            return root;
        }
        else if(leftTree == null && rightTree != null) {
            rightTree.left = root;
            return rightTree;
        }
        else {
            leftTree.right = root;
            root.left = leftTree;
            rightTree.left = root;
            root.right = rightTree;
            return left ? rightTree : leftTree;
        }
    }

    public static void main(String[] args) {

        BinaryTreeNode root = new BinaryTreeNode(10, null, null);

        root.left = new BinaryTreeNode(6, null, null);
        root.right = new BinaryTreeNode(14, null, null);

        root.left.left = new BinaryTreeNode(4, null, null);
        root.left.right = new BinaryTreeNode(8, null, null);

        root.right.left = new BinaryTreeNode(12, null, null);
        root.right.right = new BinaryTreeNode(16, null, null);

        root = convertLinkedList(root, true);

        for (BinaryTreeNode left = root; left != null; left = left.left) {
            System.out.print(left.value + " ");
        }
    }
}
