package result.util;

public interface Observable<O> {
  void addObserver(O observer);

  void removeObserver(O observer);
}
