package facade;

/**
 * Created by tisong on 1/18/17.
 */

/**
 * Computer就是外观类
 */
public class Computer {

    private CPU cpu;

    private Memory memory;

    private HardDrive hardDrive;

    /**
     * 对子系统的封装
     */
    public void startComputer() {
        cpu.execute();
        memory.load();
        hardDrive.read();
    }
}

/**
 * CPU Memory HardDrive 子系统
 */

class CPU {
    public void execute() {
        System.out.println("cpu execute");
    }
}

class Memory {
    public void load() {
        System.out.println("load memory");
    }
}

class HardDrive {
    public void read() {
        System.out.println("read hard drive");
    }
}