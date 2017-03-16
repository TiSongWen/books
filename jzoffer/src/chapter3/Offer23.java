package chapter3;

import java.util.Stack;

/**
 * Created by tisong on 3/16/17.
 */
public class Offer23 {

    static class BinaryTreeNode {
        int value;
        BinaryTreeNode left;
        BinaryTreeNode right;

        public BinaryTreeNode(int value, BinaryTreeNode left, BinaryTreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private static Stack<Integer> stack = new Stack<>();

    private static void printFromTopToBottom(BinaryTreeNode tree) {
        if (tree == null) {
            return;
        }

        printFromTopToBottom(tree.left);
        printFromTopToBottom(tree.right);

        stack.push(tree.value);
    }

    public static void printBinaryTree(BinaryTreeNode tree) {
        printFromTopToBottom(tree);

        for (int value: stack) {
            System.out.print(value + " ");
        }
    }

    public static void main(String[] args) {
        BinaryTreeNode root = new BinaryTreeNode(1, null, null);

        root.left = new BinaryTreeNode(2, null, null);
        root.right = new BinaryTreeNode(3, null, null);

        root.left.left = new BinaryTreeNode(4, null, null);
        root.left.right = new BinaryTreeNode(5, null, null);

        root.right.left = new BinaryTreeNode(6, null, null);
        root.right.right = new BinaryTreeNode(7, null, null);

        printBinaryTree(root);
    }
}
