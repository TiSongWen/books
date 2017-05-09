package jedis.chapter7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by tisong on 5/7/17.
 */
public class Test {

    public void test(String t, String... t1) {
        System.out.println("test");
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Test.class.getDeclaredMethod("test", String.class, String[].class);

        String[] ss = new String[2];
        ss[0] = "a";
        ss[1] = "b";
        method.invoke(new Test(), "b", ss);
    }
}
