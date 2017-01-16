package observer.version1;

/**
 * Created by tisong on 1/16/17.
 */
public class CurrentConditionsDiplay implements Observer{

    private float temp;

    private float humidity;

    private float pressure;

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }


    public void display() {
        System.out.println("current conditions:" + temp + "F degrees and" + humidity);
    }
}
