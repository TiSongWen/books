package chapter5;

/**
 * Created by tisong on 3/29/17.
 */
public class Offer34 {
    public int GetUglyNumber_Solution(int index) {
        int number = 2;
        while (index > 0) {
            index = isUgly(number) ? index - 1 : index;
            number++;
        }
        return number;
    }

    private boolean isUgly(int number) {

        while (number != 1) {
            if (number % 2 == 0) {
                number /= 2;
            } else if (number % 3 == 0) {
                number /= 3;
            } else if (number % 5 == 0) {
                number /= 5;
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new Offer34().GetUglyNumber_Solution(1000));
    }
}
