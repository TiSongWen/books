package chapter3;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tisong on 3/25/17.
 */
public class Test3 {

    public static void main(String[] args) {


        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();

        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }



        int firstNumber = array[0];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n-1; j++) {
                array[j] += array[j+1];
                array[j] = check(array[j]);
            }
            array[n-1] += firstNumber;
            array[n-1] = check(array[n-1]);
            firstNumber = array[0];
        }

        for (int i = 0; i < n-1; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.print(array[n-1]);

        //ConcurrentHashMap
    }

    private static int check(int n) {
        return n >= 100 ? n % 100 : n;
    }
}


