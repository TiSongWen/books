package chapter6;

/**
 * Created by tisong on 3/29/17.
 */
public class Offer42 {
    public String LeftRotateString(String str,int n) {
        n %= str.length();
        String result = str.substring(n, str.length()) + str.substring(0, n);
        return result;
    }
}
