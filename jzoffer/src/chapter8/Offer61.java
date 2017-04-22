package chapter8;

import java.util.*;
/**
 * Created by tisong on 3/27/17.
 */
public class Offer61 {

    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }
    }

    public ArrayList<ArrayList<Integer> > Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer> > result = new ArrayList<>();
        if (pRoot == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();

        boolean isEven = true;
        stack.push(pRoot);

        int start = 0;
        int end   = 1;

        while(!stack.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            Stack<TreeNode> stackNode = new Stack<>();
            int newNodeNumber = 0;
            while(start < end) {
                TreeNode node = stack.pop();
                list.add(node.val);
                start++;
                newNodeNumber += push(stackNode, node, isEven);
            }
            stack.addAll(stackNode);
            isEven = !isEven;
            end += newNodeNumber;
            result.add(list);
        }
        return result;
    }

    private int push(Stack<TreeNode> stack, TreeNode node, boolean isEven) {
        int newNodeNumber = 0;
        if (isEven) {
            newNodeNumber += checkNonNullAndPush(stack, node.left);
            newNodeNumber += checkNonNullAndPush(stack, node.right);
        } else {
            newNodeNumber += checkNonNullAndPush(stack, node.right);
            newNodeNumber += checkNonNullAndPush(stack, node.left);
        }
        return newNodeNumber;
    }

    private int checkNonNullAndPush(Stack<TreeNode> stack, TreeNode node) {
        if (node == null) {
            return 0;
        }
        stack.push(node);
        return 1;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(8);
        root.left = new TreeNode(6);
        root.right = new TreeNode(10);

        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(7);

        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(11);

        ArrayList list = new Offer61().Print(root);
    }
}
