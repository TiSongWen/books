package proxy.staticproxy;

/**
 * 静态代理的缺点：当Animal接口增加新的方法的时候，包装类也需要增加新的方法
 */
public class AnimalWrapper implements Animal{

    private Animal animal;

    public AnimalWrapper(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void eat(String food) {

    }

    @Override
    public String type() {
        return null;
    }
}
