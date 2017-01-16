package observer.version2;

import java.util.Observable;

/**
 * Created by tisong on 1/16/17.
 */
public class WeatherData extends Observable {

    private float temp;

    private float humidity;

    private float pressure;

    public void setMeasurements(float temp, float humidity, float pressure) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        setChanged();
        notifyObservers(); // 拉的形式
    }
}
