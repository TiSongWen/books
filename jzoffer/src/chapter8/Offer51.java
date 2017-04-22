package chapter8;

import java.util.HashMap;

/**
 * Created by tisong on 3/23/17.
 */
public class Offer51 {

    public static boolean duplicate(int numbers[],int length,int [] duplication) {
        if (numbers == null || numbers.length < 1) {
            return false;
        }

        HashMap<Integer,Integer> result = new HashMap<>();
        for (int i = 0; i < length; i++) {
            if (result.get(numbers[i]) != null) {
                result.put(numbers[i], result.get(numbers[i]) + 1);
            } else {
                result.put(numbers[i], 1);
            }
        }

        int position = 0;
        for (int key: result.keySet()) {
            if (result.get(key) > 1) {
                duplication[position++] = key;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] numbers = {2,4,3,1,4};
        int[] duplication = new int[numbers.length];

        System.out.println(duplicate(numbers, numbers.length, duplication));
    }


}
