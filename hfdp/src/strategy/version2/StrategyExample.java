package strategy.version2;

import java.util.function.BinaryOperator;

/**
 * Created by tisong on 1/22/17.
 */
class FunctionalUtils {
    static final BinaryOperator<Integer> add = (final Integer a, final Integer b) -> {
      return a + b;
    };
}

class Context {
    private BinaryOperator<Integer> strategy;

    public Context (BinaryOperator<Integer> strategy) {
        this.strategy = strategy;
    }

    public Integer executeStrategy (int a, int b) {
        return strategy.apply(a, b);
    }
}
public class StrategyExample {

}
