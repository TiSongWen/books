package chapter2;

/**
 * Created by tisong on 3/12/17.
 */
public class ReplaceBlank {

    // java 中的数组相对与C++的数组而, 是无法直接在原来的数组基础上进行扩容
    // 最大的思路： 从后向前复制，避免重复的移动，而之所以它可以从后向前复制的原因是：我们已经提前知道了它的长度
    public static char[] replaceBlank(char[] string) {
        if (string == null || string.length <= 0) {
            return null;
        }

        int blankNumber = 0;
        for (char c: string) {
            if (c == ' ') {
                blankNumber++;
            }
        }

        int newStringLength = string.length + blankNumber * 2;
        char[] newString = new char[newStringLength];
        for (int i = newString.length - 1, j = string.length - 1; i >= 0; i--, j--) {
            if (string[j] == ' ') {
                newString[i--] = '0';
                newString[i--] = '2';
                newString[i] = '%';
            } else {
                newString[i] = string[j];
            }
        }
        return newString;
    }

    public static void main(String[] args) {
        char[] string = "hello world ".toCharArray();
        System.out.println(replaceBlank(string));
    }
}
