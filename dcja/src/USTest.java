import java.util.ArrayList;
import java.util.List;

/**
 * Created by tisong on 1/3/17.
 */
public class USTest {

    public static void main(String[] args) {
        new USTest().runTest();
    }

    private void runTest() {
        /**
         * Runtime类封装了运行时的环境, 而每一个Java应用程序都拥有 自己的Runtime实例, 使程序和环境连接
         */

        // processors ： 处理器, 而非进程(Tasks)
        int count = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < count; i++) {
            new Thread(new ConsumeCPUTask()).start();
        }

        for (int i = 0; i < 200; i++) {
            new Thread(new NotConsumeCPUTask()).start();
        }
    }


    class ConsumeCPUTask implements Runnable {

        @Override
        public void run() {

            String str = "fioejwhrfeowriowehrukehawlrfbekawbnfewhafawk3123123221231e" +
                    "jeiowu423ou4o812734902h3jhr2ioirj8o2ur9032uo2390u83429oh4iu2" +
                    "3w28ou432h2918u8o321h4e2hejukdfhaj-0-=-ipjon][];'.;'miljio32EW";
            float i = 0.002f;
            float j = 232.13243f;
            while (true) {
                j *= i;
                str.indexOf("#");
                List<String> list = new ArrayList<String>();
                for (int k = 0; k < 10000; k++) {
                    list.add(str + String.valueOf(k));
                }
                list.contains("iii");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class NotConsumeCPUTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000 * 1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
