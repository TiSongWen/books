package chapter3;

// 思路：先获取1000以内所有的质数
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test4 {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        for (int i = 4; i <= 1000; i++) {
            int j;
            for (j = 2; j < i && i % j != 0; j++) {

            }
            if (j == i) {
                list.add(j);
              //  System.out.println(j);
            }
        }

        int result = 0;
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) >= n / 2 + 1) {
                break;
            }
            int pos = binaryFind(list, n - list.get(i));
            if (pos != -1) {
                result++;
                System.out.println(list.get(i) + " " + list.get(pos));
            }
        }

        System.out.println(result);
    }

    private static int binaryFind(List<Integer> list, int number) {
        int start = 0;
        int end = list.size() - 1;
        while(start <= end) {
            int middle = start + (end - start ) / 2;
            if (list.get(middle) == number) {
                return middle;
            } else if (list.get(middle) > number) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }
        return -1;
    }
}