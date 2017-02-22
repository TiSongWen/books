package singleton;

/**
 * 多线程-双重锁
 */
public class Singleton2 {

    private volatile static Singleton2 singleton;

    private Singleton2() {}

    /**
     * 注意 ： 双重锁检测并不适用于JDK1.4版本及以下版本
     * @return
     */
    public static Singleton2 getInstance() {
        if (singleton == null) {
            // 可能有多个线程进入
            synchronized (Singleton2.class) {
                if (singleton == null) {
                    singleton = new Singleton2(); // 为了保证在线程A创建完singleton实例后，线程B可以同步看到, 所以singleton实例用volatile修饰
                }
            }
        }
        return singleton;
    }
}
