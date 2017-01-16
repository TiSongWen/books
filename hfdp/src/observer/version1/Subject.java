package observer.version1;

/**
 * Created by tisong on 1/16/17.
 */
public interface Subject {

    public void registerObserver (Observer o);

    public void removeObserver (Observer o);

    public void notifyObservers();
}
