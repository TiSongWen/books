package observer.version2;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by tisong on 1/16/17.
 */
public class CurrentConditionsDisplay implements Observer{

    private Observable observable;

    private float temp;

    private float humidity;

    public CurrentConditionsDisplay(Observable o) {
        this.observable = o;
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof WeatherData) {
            //
        }
    }
}
