package chapter8;

/**
 * Created by tisong on 3/28/17.
 */
public class Offer54 {
    public boolean isNumeric(char[] str) {
        int index = 0;
        if (str[index] == '+' || str[index] == '-') {
            index++;
        }

        boolean e = false;
        boolean point = false;

        for (; index < str.length; index++) {
            if (str[index] == 'e' || str[index] == 'E') {
                if (e) {
                    return false;
                }
                if (index == str.length - 1) {
                    return false;
                }

                if (str[index+1] == '+' || str[index+1] == '-') {
                    index++;
                }
                if (index == str.length - 1) {
                    return false;
                }
                e = true;
            }
            if (str[index] == '.') {
                if (e || point) {
                    return false;
                }
                point = true;
            }
            if (str[index] < '0' && str[index] > '9') {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.print(new Offer54().isNumeric("12e".toCharArray()));
    }
}
