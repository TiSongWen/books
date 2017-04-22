package chapter8;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tisong on 3/27/17.
 */
public class Offer59 {

    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }
    }

    boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null) {
            return false;
        }

        List<TreeNode> leftNodes = new LinkedList<>();
        List<TreeNode> rightNodes = new LinkedList<>();

        preFind(pRoot.left, leftNodes);
        lastFind(pRoot.right, rightNodes);

        if (leftNodes.size() != rightNodes.size()) {
            return false;
        }

        boolean result = true;
        for (int i = 0; i < leftNodes.size(); i++) {
            if (leftNodes.get(i).val != rightNodes.get(i).val) {
                result = false;
            }
        }
        return result;
    }

    void preFind(TreeNode tree, List<TreeNode> list) {
        if (tree == null) {
            return ;
        }

        preFind(tree.left, list);
        list.add(tree);
        preFind(tree.right, list);
    }

    void lastFind(TreeNode tree, List<TreeNode> list) {
        if (tree == null) {
            return ;
        }

        lastFind(tree.right, list);
        list.add(tree);
        lastFind(tree.left, list);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(5);
        root.right = new TreeNode(5);
        char c;

        root.left.left = new TreeNode(5);
//        root.left.right = new TreeNode(7);

        root.right.left = new TreeNode(5);
        //root.right.right = new TreeNode(11);

        boolean result = new Offer59().isSymmetrical(root);
    }
}
