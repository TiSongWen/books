package chapter5;

/**
 * 涉及到动态规划
 */
public class Offer31 {

    public static int findGreatestSumOfSubArray(int[] array) {
        int sum = 0;
        int maxSum = Integer.MIN_VALUE;

        /**
         * 如果前面的和小于0, 则全部忽略掉前面的内容
         */
        for (int i = 0; i < array.length; i++) {
            if (sum <= 0) {
                sum = array[i];
            } else {
                sum += array[i];
            }

            if (sum > maxSum) {
                maxSum = sum;
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] array = {1,-1,3,10,-4, 7,2,-5};
        int[] array1 = {-3, 10, -1};

        System.out.println(findGreatestSumOfSubArray(array1));
    }
}
