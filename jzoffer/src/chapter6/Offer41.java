package chapter6;

import java.util.ArrayList;

/**
 * Created by tisong on 3/29/17.
 */
public class Offer41 {
    public ArrayList<Integer> FindNumbersWithSum(int [] array, int sum) {
        ArrayList<Integer> result = new ArrayList<>();
        if (array == null || array.length < 1) {
            return result;
        }
        
        int min = Integer.MAX_VALUE;
        int[] numbers = new int[2];
        for (int i = 0; i < array.length; i++) {
            int findNumber = sum - array[i];
            if (findNumber < array[i]) {
                break;
            }
            int findPosition = findMiddle(array, findNumber);
            if (findPosition == -1) {
                continue;
            }
            int a = array[i] * array[findPosition];
            if (min > a) {
                min = a;
                numbers[0] = array[i];
                numbers[1] = array[findPosition];
            }
        }

        result.add(numbers[0]);
        result.add(numbers[1]);

        return result;
    }

    private int findMiddle(int[] array, int number) {
        int start = 0;
        int end   = array.length - 1;
        while (start <= end) {
            int middle = start + (end - start) / 2;
            if (array[middle] > number) {
                end = middle - 1;
            } else if (array[middle] < number) {
                start = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    public static void main(String[] args) {

        new Offer41().FindNumbersWithSum(new int[]{1,2,4,7,11,15}, 15);
    }
}
