package chapter3;

/**
 * Created by tisong on 3/14/17.
 */
public class MinorGCDemo {

    private static void happenMinorGC(int happenMinorGCIndex) throws InterruptedException {

        for (int i = 0; i < happenMinorGCIndex; i++) {
            if (i == happenMinorGCIndex - 1) {
                Thread.sleep(2000);
                System.out.println("minor gc should happen");
            }
            new MemoryObject(1024 * 1024);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000);
        MemoryObject object = new MemoryObject(1024 * 1024);
        for (int i = 0; i < 2; i++) {
            happenMinorGC(11);
            Thread.sleep(2000);
        }
    }
}

