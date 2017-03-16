package chapter3;

/**
 * Created by tisong on 3/16/17.
 */
public class Offer24 {

    public static boolean verifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length < 1) {
            return false;
        }

        return splitLeftOrRightBST(sequence, 0, sequence.length - 1);
    }

    private static boolean splitLeftOrRightBST(int[] sequence, int start, int end) {
        if (start >= end) {
            return true;
        }

        int rightStart = end;
        // 从右向左
        for (; rightStart >= start && sequence[end] <= sequence[rightStart]; rightStart--) {

        }

        if (rightStart < end) {
            rightStart++;
        }

        int leftEnd = rightStart - 1;
        int leftStart = start;
        for (; leftStart < leftEnd && sequence[leftStart] < sequence[end]; leftStart++) {

        }

        if (leftStart < leftEnd) {
            return false;
        }

        return splitLeftOrRightBST(sequence, start, leftEnd) &&
        splitLeftOrRightBST(sequence, rightStart, end-1);
    }

    public static void main(String[] args) {
        int[] sequence = {7,4,6,5};

        System.out.print(verifySquenceOfBST(sequence));
    }
}
