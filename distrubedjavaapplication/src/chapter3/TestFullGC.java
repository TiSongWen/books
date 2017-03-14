package chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by tisong on 3/14/17.
 */
public class TestFullGC {

    public static void main(String[] args) throws InterruptedException {

        //Scanner scanner = new Scanner(System.in);
        //scanner.nextLine();

        List<MemoryObject> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add(new MemoryObject(1024*1024));
        }

        // 让上面的对象尽可能进入旧生代
        System.gc();
        System.gc();

        Thread.sleep(2000);

        objects.clear();

        for (int i = 0; i < 10; i++) {
            objects.add(new MemoryObject(1024*1024));
            if (i % 3 == 0) {
                objects.remove(0);
            }
        }
        Thread.sleep(5000);
    }
}


