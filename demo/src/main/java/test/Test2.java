package test;

import java.util.Scanner;

/**
 * Created by tisong on 4/26/17.
 */
public class Test2 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String string  = scanner.nextLine();
        String pattern = scanner.nextLine();

        System.out.println(filter(string, pattern));
    }

    private static int filter(String string, String pattern) {

        for (int i = 0, j = 0; i < string.length() && j < pattern.length();) {
            if (pattern.charAt(j) == '?') {
                // ? 一定匹配一个字符
                j++;
                i++;
            }
            else if (pattern.charAt(j) == '*') {
                // string 可以不断跳过
            }
            else if (string.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }
        }


        return 1;
    }

}
