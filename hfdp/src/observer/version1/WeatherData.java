package observer.version1;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tisong on 1/16/17.
 */
public class WeatherData implements Subject {

    private List<Observer> observers;

    private float temp;

    private float humidity;

    private float pressure;

    public WeatherData() {
        observers = new LinkedList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temp, humidity, pressure);
        }
    }


    public void setMeasurements(float temp, float humidity, float pressure) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }
}
