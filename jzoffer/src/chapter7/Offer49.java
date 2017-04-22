package chapter7;

import java.util.HashMap;

/**
 * String to Number
 * 边界值, 非法值, 整数与负数
 */
public class Offer49 {

    public static long stringToNumber(String string) {
        HashMap s = null;
        s.entrySet();

        if (string == null) {
            throw new NullPointerException("string is null");
        }
        if (string.length() < 1) {
            throw new IllegalArgumentException("string length can not be zero");
        }

        boolean nagative = false;
        int position = 0;
        if(string.charAt(position) == '-') {
            nagative = true;
        }
        if(string.charAt(position) == '-' || string.charAt(position) == '+') {
            position++;
        }

        long number = 0;

        while(position < string.length()) {
            if (string.charAt(position) >= '0' && string.charAt(position) <= '9') {
                number = number * 10 + (string.charAt(position) - '0');
            } else {
                throw new IllegalArgumentException("string must be number: " + string);
            }
        }

        return nagative ? 0 - number : number;
    }
}
