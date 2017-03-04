package concurrent;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tisong on 3/4/17.
 */
public class FutureTaskDemo {

    // 一个连接池
    Map<String, Connection> connectionPool = new HashMap<>();

    ReentrantLock lock = new ReentrantLock();

    Map<String, FutureTask> connectionPool2 = new ConcurrentHashMap<>();

    public void demo() {



    }

    /**
     * 1. 改进点：如果使用ConcurrentHashMap，几乎可以避免加锁操作.
     * 2. 改进后的缺点：但可能出现Connection被多次创建的现象, 因为多个线程会去执行该getConnection方法
     * 3. 缺点的原因：所以多个connection会被创建，然后再放进connectionPool中
     * 4. 再次改进的思路：如果可以先放进connectionPool中（只是会创建多个FutureTask)，放进去之后再执行创建的操作
     * @param key
     * @return
     */
    public Connection getConnection(String key) {
        try {
            lock.lock();
            if (connectionPool.containsKey(key)) {
                return connectionPool.get(key);
            } else {
                // create connection
                Connection connection = null; // create connection
                connectionPool.put(key, connection);
                return connection;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 改进后的版本
     * @param key
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Connection getConnection2(String key) throws ExecutionException, InterruptedException {
        FutureTask<Connection> connectionFutureTask = connectionPool2.get(key);
        if (connectionFutureTask != null) {
            return connectionFutureTask.get();
        } else {
            Callable<Connection> callable = new Callable<Connection>() {
                @Override
                public Connection call() throws Exception {
                    return null; // create and return a connection
                }
            };

            FutureTask<Connection> newTask = new FutureTask<Connection>(callable);
            connectionFutureTask = connectionPool2.putIfAbsent(key, newTask);
            connectionFutureTask.run();
        }
        return connectionFutureTask.get();
    }


    // isDone方法来判断任务是否完成

    // cancel方法
}
