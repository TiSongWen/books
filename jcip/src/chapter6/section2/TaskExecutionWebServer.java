package chapter6.section2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 1. 生命周期的支持
 * 2. 统计信息收集
 * 3. 应用程序管理机制
 * 4. 性能监控
 */
public class TaskExecutionWebServer {

    private static final int NTHREADS = 100;

    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        //ServerSocket socket = new ServerSocket(80);

        while (true) {
          //  final Socket connectin = socket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    // 处理请求
                }
            };
            exec.execute(task);
        }
    }
}