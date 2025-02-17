package result.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ObservableInteger extends Observable {
    private int value;
    private final List<Observer> observers = new ArrayList<>();

    public ObservableInteger(int value) {
        this.value = value;
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
        this.observers.forEach(observer -> observer.update(this.value));
    }

    public interface Observer {
        void update(int value);
    }
}