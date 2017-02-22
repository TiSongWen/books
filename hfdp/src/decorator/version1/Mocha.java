package decorator.version1;

/**
 * Created by tisong on 1/18/17.
 */
public class Mocha extends CondimentDecorator {
    private Beverage beverage;

    public Mocha(Beverage beverage) { this.beverage = beverage; }

    /**
     * 被修饰的方法
     */
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + " Mocha";
    }

    @Override
    public double cost() {
        return beverage.cost() + .20;
    }
}
