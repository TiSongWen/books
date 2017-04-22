package chapter8;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by tisong on 3/27/17.
 */
public class Offer60 {
    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }
    ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        if (pRoot == null) {
            return null;
        }


        ArrayList<ArrayList<Integer> > result = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(pRoot);
        int start = 0;
        int end   = 1;
        while(!queue.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            int newNodeNumber = 0;
            while(start < end) {
                TreeNode node = queue.remove();
                start++;
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                    newNodeNumber++;
                }
                if (node.right != null) {
                    queue.add(node.right);
                    newNodeNumber++;
                }
            }
            end += newNodeNumber;
            result.add(list);
        }

        return result;
    }

}
