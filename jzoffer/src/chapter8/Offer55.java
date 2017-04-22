package chapter8;

import java.util.Arrays;

/**
 * Created by tisong on 3/28/17.
 */
public class Offer55 {
    int[] ch = new int[256];
    int[] first = new int[256];

    int index = 0;
    //Insert one char from stringstream
    public void Insert(char ch)
    {
        this.ch[ch]++;
        if (first[ch] == 0) {
            first[ch] = ++index;
        }
    }
    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce()
    {
        char minIndex = '#';
        for (int i = 0, pos = Integer.MAX_VALUE; i < 256; i++) {
            if (ch[i] == 1 && pos > first[i]) {
                pos = i;
               // System.out.println((char)i);
                minIndex = (char)i;
            }
        }
        return minIndex;
    }

    public static void main(String[] args) {
        Offer55 offer55 = new Offer55();

        String s = new String("google");
        char[] chars = s.toCharArray();
        for (char c: chars) {
            offer55.Insert(c);
        }

        System.out.println(offer55.FirstAppearingOnce());
    }
}
