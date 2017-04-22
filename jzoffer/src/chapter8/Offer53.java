package chapter8;

/**
 * Created by tisong on 3/28/17.
 */
public class Offer53 {
    public boolean match(char[] str, char[] pattern) {
        if (str.length == 0 && (pattern.length == 0 || pattern[0] == '*')) {
            return true;
        }
        if (pattern.length == 2 && pattern[1] == '*') {
            return true;
        }

        if (str.length == 0) {
            return false;
        }
        int indexStr = 0;
        int indexPattern = 0;

        while(indexStr < str.length && indexPattern < pattern.length) {
            if (pattern[indexPattern] == '.') {
                if (indexPattern + 1 < pattern.length && pattern[indexPattern+1] == '*') {
                    return true;
                }
                indexStr++;
                indexPattern++;
            }
            else if (str[indexStr] != pattern[indexPattern]) {
                if (indexPattern + 1 == pattern.length || pattern[indexPattern+1] != '*') {
                    return false;
                }
                // 是 *
                indexPattern += 2;
            }
            else if (str[indexStr] == pattern[indexPattern]) {
                char c = str[indexStr];
                indexStr++;
                indexPattern++;
                if (indexPattern == pattern.length || indexStr == str.length) {
                    break;
                }
                if (pattern[indexPattern] != '*') {
                    continue;
                }
                int strCharNumber = 0;
                while(indexStr < str.length && str[indexStr++] == c) {
                    strCharNumber++;
                }
                int patternCharNumber = 0;
                indexPattern++;
                while(indexPattern < pattern.length && pattern[indexPattern++] == c) {
                    patternCharNumber++;
                }
                if (patternCharNumber == pattern.length) {
                    return true;
                }
                if (pattern[indexPattern] == '.') {
                    continue;
                }
                //if ()
                if (patternCharNumber > strCharNumber)    {
                    return false;
                }
            }
        }

        if (indexStr < str.length) {
            return false;
        }
        // 模式未匹配完

        if (indexPattern + 1 == pattern.length - 1 && pattern[indexPattern+1] == '*') {
            return true;
        } else if (indexPattern < pattern.length) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(new Offer53().match("aaa".toCharArray(), "ab*a*c*a".toCharArray()));
    }
}
