package concurrent;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 应用一：Semaphore提供用于控制某资源同时被访问个数的类
 */
public class SemaphoreDemo {

    /**
     * 连接池中要控制创建连接的个数
     */
    int maxActive;

    int numActive;

    int maxWait;

    LinkedList pool;

    /**
     * 没有使用Semaphore信号量的时候如何控制资源的数量
     * 1. 无资源时，要进行睡眠, 然后继续重新判断当前是否有资源
     * @return
     * @throws InterruptedException
     */
    public Object get() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Object object = null;
        while(true) {
            synchronized (this) {
                object = pool.removeFirst(); //
                if (object == null && numActive >= maxActive) {
                    long waitTime = maxWait - (System.currentTimeMillis() - startTime);
                    wait(waitTime); // 等待另一个人来唤醒它
                    if (System.currentTimeMillis() - startTime > maxWait) {
                        // throw time out exception
                    } else {
                        continue;
                    }
                }
                numActive++;
            }
            return object;
        }
    }
}

/**
 * 应用二： Semaphore 作为有界资源池
 * 信号量底层是如何实现同步的呢: 是以及CAS(乐观锁的思想）来避免锁的使用，提高并发性
 * @param <T>
 */
class BoundedHashSet<T> {

    private final Set<T> set;

    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        // TODO 好奇这是什么
        this.set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        /**
         * 倘若当前许可数为0(即已经达到了bound界限)，该方法会一直阻塞, 直到拥有许可可用
         */
        sem.acquire();

        // 另一种写法
        sem.tryAcquire(1000, TimeUnit.MILLISECONDS);
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                // 添加失败, 所以再释放许可
                sem.release();
            }
        }
    }

    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved) {
            sem.release();
        }
        return wasRemoved;
    }
}
