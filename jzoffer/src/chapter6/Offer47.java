package chapter6;

/**
 * Created by tisong on 3/29/17.
 */
public class Offer47 {
    public int Add(int num1,int num2) {
        int result = 0;
        boolean flag = false;

        for (int i = 0; i < 32; i++) {

            int n1 = num1 & (1 << i);
            int n2 = num2 & (1 << i);
            if ((n1 & n2) >> i == 1) {
                if (flag) {

                }
                flag = true;
                result &= ~(1 << i);
            }
        }
        return result;
    }

    public static void main(String[] args) {

        int number = -2;

        for (int i = 0; i < 32; i++) {
            System.out.print(number & 1);
            number = number >> 1;
        }
    }
}
