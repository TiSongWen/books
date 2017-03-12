package chapter2;

/**
 * Created by tisong on 3/12/17.
 */
public class MinNumber {

    public static int binarySearch(int[] array, int number) {
        int start = 0;
        int end = array.length;

        while (start < end) {
            int middle = start + (end - start) / 2;
            if (array[middle] == number) {
                return middle;
            } else if (array[middle] > number) {
                end = middle;
            } else {
                start = middle;
            }
        }
        return -1;
    }


    /**
     * 算是对二分查找的一个变向的理解: 两个指针与中位数的的使用
     * @param array
     * @return
     */
    public static int minNumber(int[] array) {
        if (array == null || array.length <= 0) {
            return -1;
        }


        int start = 0;
        int end = array.length;

        if (array[start] < array[end-1]) {
            return array[start];
        }

        while (start < end) {
            if (end - 1 == start) {
                return array[end];
            }
            int middle = start + (end - start) / 2;
            if (array[start] > array[middle]) {
                end = middle;
            } else if (array[start] < array[middle]) {
                start = middle;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, -1,0};

        System.out.println(minNumber(array));
    }
}
