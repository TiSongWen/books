package chapter3;

import java.util.Stack;

/**
 * Created by tisong on 3/16/17.
 */
public class Offer25 {

    static class TreeStack {
        int sum;

        Stack<Integer> stack;

        public TreeStack() {
            this.sum = 0;
            this.stack = new Stack<>();
        }

        public void push(int number) {
            sum += number;
            stack.push(number);
        }

        public int pop() {
            int number = stack.pop();
            sum -= number;
            return number;
        }
    }

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


    static TreeStack stack = new TreeStack();


    public static void preFind(BinaryTreeNode root, int expectedSum) {
        if (root == null) {
            return ;
        }

        stack.push(root.value);
        if (stack.sum == expectedSum) {
            for (int value: stack.stack) {
                System.out.print(value + " ");
            }
            System.out.println("");
        }
        preFind(root.left, expectedSum);
        preFind(root.right, expectedSum);

        stack.pop();
    }

    public static void main(String[] args) {
        BinaryTreeNode root = new BinaryTreeNode(1, null, null);

        root.left = new BinaryTreeNode(2, null, null);
        root.right = new BinaryTreeNode(3, null, null);

        root.left.left = new BinaryTreeNode(4, null, null);
        root.left.right = new BinaryTreeNode(4, null, null);

        root.right.left = new BinaryTreeNode(6, null, null);
        root.right.right = new BinaryTreeNode(7, null, null);
        preFind(root, 7);
    }
}
