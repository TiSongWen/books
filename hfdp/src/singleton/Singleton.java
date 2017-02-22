package singleton;

/**
 * Created by tisong on 1/18/17.
 */
public class Singleton {

    private static Singleton singleton;

    private Singleton() {}

    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;

    }
}
