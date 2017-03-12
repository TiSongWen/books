package chapter2;

/**
 * 居然还可以转化成青蛙跳台阶的问题(一次可以跳上一级，也可以跳上两级，跳到一个n级的台阶共有多少中跳法
 * 青蛙可以跳上一级，可以跳上两级..... 也可以跳上n级，那么青蛙跳上一个n级的台阶共有多少种跳法
 */
public class Fibonacci {

    public static long getFinbonacciMethod1(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        return getFinbonacciMethod1(n - 1) + getFinbonacciMethod1(n - 2);
    }

    public static long getFinbonacciMethod2(int n) {

        long number1 = 0;
        long number2 = 1;
        long numberMiddle = 0;
        for (int i = 2; i <= n; i++) {
            numberMiddle = number1 + number2;
            number1 = number2;
            number2 = numberMiddle;
        }

        return numberMiddle;
    }
}
