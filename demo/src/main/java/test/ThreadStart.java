package test;

/**
 * Created by tisong on 3/28/17.
 */
public class ThreadStart implements Runnable{

    public static void main(String[] args) {
        int count = 0;
        while(true) {
            new Thread(new ThreadStart()).start();
            count++;
            if (count % 1000 == 0) {
                System.out.println(count);
            }
        }
    }

    @Override
    public void run() {

        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
