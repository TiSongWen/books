package adapter.version1;

/**
 * 适配器
 */
public class Adapter implements Target{
    private Adaptee adaptee;


    @Override
    public void request() {
        adaptee.specificRequest();
    }
}
