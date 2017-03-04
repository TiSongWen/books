package concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 控制多个线程： 同时开始执行某个动作
 * 当减数减到0的时候，latch.await后的代码才会被执行
 * 依然是基于CAS来实现同步
 */
public class CountDownLatchDemo {

    // 两个方法是该类的主要方法
    // 1. countdown();
    // 2. await();

    /**
     * 闭锁的几个应用场景
     * 1. 比如: 所有资源都在被初始化后，该操作才能继续执行（资源等待问题)
     * 2. 比如：其他服务启动后，该服务才能启动（服务启动顺序问题)
     * 3. 比如: 当游戏玩家中全部准备时，才能开始游戏
     */


    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread thread = new Thread() {
               public void run() {
                   try {
                       startGate.await();
                       try {
                           task.run();
                       } finally {
                           startGate.countDown();
                       }
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
            };
            thread.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return start - end;
    }
}
