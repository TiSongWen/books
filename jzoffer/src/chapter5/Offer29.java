package chapter5;

/**
 * Created by tisong on 3/23/17.
 */
public class Offer29 {

    public static int moreThanHalfNum(int[] numbers) {
        int middle = numbers.length / 2;

        return 1;
    }

    private static int partition(int[] numbers, int start, int end) {
        int middle = start + (end - start) / 2;

        while(start < end) {
            while (start <= end && middle < end && numbers[end] > numbers[middle]) {
                end--;
            }
            middle = swap(numbers, middle, end);
            while(start <= end  && middle > start && numbers[start] < numbers[middle]) {
                start++;
            }
            middle = swap(numbers, middle, start);
        }
        return middle;
    }

    private static int swap(int[] numbers, int positionA, int positionB) {
        int t = numbers[positionA];
        numbers[positionA] = numbers[positionB];
        numbers[positionB] = t;
        return positionB;
    }


    public static void main(String[] args) {
        int[] numbers = {4,3,6,5,2};
        partition(numbers, 0, numbers.length-1);
    }
}
