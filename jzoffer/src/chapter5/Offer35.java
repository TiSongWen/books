package chapter5;

/**
 * Created by tisong on 3/29/17.
 */
public class Offer35 {
    public int FirstNotRepeatingChar(String str) {
        if (str == null) {
            return -1;
        }

        int[] strPosition = new int[256];

        for (int i = 0; i < str.length(); i++) {
            int c = str.charAt(i);
            if (strPosition[c] == -1) {
                continue;
            } else if (strPosition[c] == 0) {
                strPosition[c] = i+1;
            } else {
                strPosition[c] = -1;
            }
        }

        int firstPosition = Integer.MAX_VALUE;
        for (int position = 0; position < strPosition.length; position++) {
            if (strPosition[position] > 0 && firstPosition > strPosition[position]) {
                System.out.println((char)position + "" + strPosition[position]);
                firstPosition = strPosition[position];
            }
        }

        // -1 代表不存在
        return firstPosition != Integer.MAX_VALUE ? firstPosition - 1: -1;
    }

    public static void main(String[] args) {
        new Offer35().FirstNotRepeatingChar("google");
    }
}
