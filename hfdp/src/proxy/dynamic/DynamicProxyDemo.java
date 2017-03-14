package proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by tisong on 3/7/17.
 */
interface Mammal {
    void eat(String type);

    String type();
}

interface Primate {
    void think();
}

class Monkey implements Mammal, Primate {

    @Override
    public void eat(String type) {
        System.out.println("type");
    }

    @Override
    public String type() {
        return "猴子";
    }

    @Override
    public void think() {
        System.out.println("思考");
    }
}

/**
 * 代理对象和回调接口互相绑定
 */
class MyInvocationHandler implements InvocationHandler {

    private Object object;

    public MyInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Method Before");
        Object returnObject = method.invoke(object, args);
        System.out.println("Method After");
        return returnObject;
    }
}
public class DynamicProxyDemo {


}
