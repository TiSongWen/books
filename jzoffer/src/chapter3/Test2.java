package chapter3;

import java.util.*;

/**
 * Created by tisong on 3/25/17.
 */
public class Test2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        Queue<Integer> queue = new LinkedList<>();

        int result1 = find(s, 'G', queue);
        queue.clear();
        int result2 = find(s, 'B', queue);

        System.out.println(Math.min(result1, result2));
    }

    // C 是 G
    private static int find(String s, char c, Queue<Integer> queue) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != c) {
                // B
                queue.add(i);
            } else if (queue.size() > 0) {
                // G前面有B
                int pos = queue.remove();
                sum += (i - pos);
                queue.add(i);
            }
        }
        return sum;
    }


}
