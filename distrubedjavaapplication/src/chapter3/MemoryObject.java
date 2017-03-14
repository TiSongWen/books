package chapter3;

/**
 * Created by tisong on 3/14/17.
 */
public class MemoryObject {
    private byte[] bytes;
    public MemoryObject(int objectSize) {
        bytes = new byte[objectSize];
    }
}
