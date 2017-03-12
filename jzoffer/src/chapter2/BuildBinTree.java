package chapter2;

import java.util.List;

/**
 * Created by tisong on 3/12/17.
 */
public class BuildBinTree {

   static class BinaryTreeNode {
       public BinaryTreeNode left;
       public BinaryTreeNode right;
       public int value;
       public BinaryTreeNode(int value, BinaryTreeNode left, BinaryTreeNode right) {
           this.value = value;
           this.left  = left;
           this.right = right;
       }
       public BinaryTreeNode(int value) {
           this.value = value;
           this.left = this.right = null;
       }
   }

//    public static BinaryTreeNode buildBinTree(List<Integer> preOrder, List<Integer> inOrder) {
//        if (preOrder == null || inOrder == null || preOrder.size() < 1 || inOrder.size() < 1) {
//            return null;
//        }
//
//        BinaryTreeNode root = new BinaryTreeNode(preOrder.get(0));
//
//        int pos = 0;
//
//        root.left = new BinaryTreeNode(inOrder.get(pos));
//        root.right =
//
//
//    }
//
//
//    BinaryTreeNode buildBree(BinaryTreeNode root, List<Integer> preOrder, List<Integer> inOrder) {
//
//    }

    int findRootPosition(BinaryTreeNode root, List<Integer> inOrder, int start, int end) {
        for (int i = start; i < end; i++) {
            if (root.value == inOrder.get(i)) {
                return i;
            }
        }
        return -1;
    }
}
