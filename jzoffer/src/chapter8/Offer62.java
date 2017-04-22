package chapter8;

/**
 * Created by tisong on 3/27/17.
 */
public class Offer62 {

    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }


    static String Serialize(TreeNode root) {
        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString();
    }

    private static void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("#,");
            return ;
        }
        sb.append(root.val + ",");
        serialize(root.left, sb);
        serialize(root.right, sb);
    }

    static int pos = 0;

    static TreeNode Deserialize(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        TreeNode treeNode = new TreeNode(str.charAt(0));
        TreeNode point = treeNode;
        while(pos < str.length()) {
            point.right = buildLeft(str);
            point = point.right;
        }
        return treeNode.right;
    }



    static TreeNode buildLeft(String str) {
        TreeNode node = new TreeNode(0);
        TreeNode leftNode = node;
        for (; pos < str.length(); pos++) {
            if (str.charAt(pos) == ','){
                continue;
            }
            if (str.charAt(pos) != '#') {
            // TODO    leftNode.left = new TreeNode(Integer.parseInt(str.));
                leftNode = leftNode.left;
            } else {
                break;
            }
        }
        return node.left;
    }

    public static void main(String[] args) {
        TreeNode tree = Deserialize("8,6,10,5,7,9,11");
    }
}

