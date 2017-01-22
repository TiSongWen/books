package strategy.version1;

/**
 * 策略模式 java
 */
public interface Strategy {
    public int execute(final int a, final int b);
}

class Add implements Strategy{
    @Override
    public int execute(int a, int b) {
        return a + b;
    }
}

class Subtrate implements Strategy {

    @Override
    public int execute(int a, int b) {
        return a - b;
    }
}

class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int execute(int a, int b) {
        return strategy.execute(a, b);
    }
}
