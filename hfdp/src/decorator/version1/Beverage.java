package decorator.version1;

/**
 * Created by tisong on 1/18/17.
 */
public abstract class Beverage {

    /**
     * 可以让子类直接访问
     */
    protected String description = "Unknown Beverage";

    public String getDescription() { return description; }

    public abstract double cost();
}
