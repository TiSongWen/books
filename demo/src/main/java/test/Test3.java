package test;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by tisong on 4/26/17.
 */
public class Test3 {

    public static void main(String[] args) {
        ArrayList<Integer> inputs = new ArrayList<Integer>();
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        if(line != null && !line.isEmpty()) {
            int res = resolve(line.trim());
            System.out.println(String.valueOf(res));
        }
    }

    public static int resolve(String expr) {
        if (expr.length() < 1) {
            return -1;
        }

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            if (expr.charAt(i) == ' ') {
                continue;
            }

            if (expr.charAt(i) >= '0' && expr.charAt(i) <= '9') {
                if (stack.size() >= 16) {
                    return -2;
                }
                StringBuilder stringBuilder = new StringBuilder();
                while(expr.charAt(i) >= '0' && expr.charAt(i) <= '9') {
                    stringBuilder.append(expr.charAt(i));
                    i++;
                }
                i--;
                stack.push(Integer.parseInt(stringBuilder.toString()));
            } else if (expr.charAt(i) == '^') {
                if (stack.size() < 1) {
                    return -1;
                }
                int number = stack.pop();
                number++;
                stack.push(number);
            } else if (expr.charAt(i) == '+') {
                if (stack.size() < 2) {
                    return -1;
                }
                int result = stack.pop() + stack.pop();
                stack.push(result);
            } else if (expr.charAt(i) == '*') {
                if (stack.size() < 2) {
                    return -1;
                }
                int result = stack.pop() * stack.pop();
                stack.push(result);
            }
        }
        return stack.pop();
    }
}
