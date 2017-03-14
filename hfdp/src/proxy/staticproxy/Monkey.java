package proxy.staticproxy;

/**
 * Created by tisong on 3/7/17.
 */
public class Monkey implements Animal {
    @Override
    public void eat(String food) {
        System.out.println("The food is " + food);
    }

    @Override
    public String type() {
        String type = "哺乳动物";
        System.out.println(type);
        return type;
    }
}
