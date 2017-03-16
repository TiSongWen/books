package chapter3;

/**
 * Created by tisong on 3/16/17.
 */
public class HasSubTree {
    static class BinaryTreeNode {
        int value;
        BinaryTreeNode left;
        BinaryTreeNode rigth;
    }

    public boolean hasSubTree(BinaryTreeNode btree1, BinaryTreeNode btree2) {
        boolean result = false;

        if (btree1 != null && btree2 != null) {
            if (btree1.value == btree2.value) {
                result = doesTree1HasTree2(btree1, btree2);
            }
            if (!result) {
                result = hasSubTree(btree1.left, btree2);
            }
            if (!result) {
                result = hasSubTree(btree1.rigth, btree2);
            }
        }

        return result;
    }

    private boolean doesTree1HasTree2(BinaryTreeNode btree1, BinaryTreeNode btree2) {
        if (btree2 == null) {
            return true;
        }

        if (btree1 == null) {
            return false;
        }

        if (btree1.value != btree2.value) {
            return false;
        }

        return doesTree1HasTree2(btree1.left, btree2.left) && doesTree1HasTree2(btree1.rigth, btree2.rigth);
    }
}


