package result.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ProxyObservable provides an abstraction layer for managing observer
 * registration. It acts as a proxy between external observers of type {@code T}
 * and an underlying observable that accepts observers of type {@code U}.
 *
 * <p>
 * Subclasses must implement the {@link #createInternalObserver(T)} method
 * to convert an external observer of type {@code T} to a new observer of type
 * {@code U} that can be registered with the underlying observable.
 * </p>
 *
 * @param <T> the external observer type.
 * @param <U> the internal observer type expected by the underlying observable.
 * @param <O> the type of the underlying observable which accepts observers of
 *            type {@code U}.
 */
public abstract class ProxyObservable<T, U, O extends Observable<U>> {
  private final Map<T, U> proxyObservers = new HashMap<>();
  private final O observable;

  /**
   * Constructs a new ProxyObservable with the specified underlying observable.
   *
   * @param observable the underlying observable instance that will receive the
   *                   internal observers.
   */
  protected ProxyObservable(O observable) {
    this.observable = observable;
  }

  /**
   * Adds an external observer by creating an internal proxy observer and
   * registering it with the underlying observable.
   *
   * @param observer the external observer to be added.
   */
  public void addObserver(T observer) {
    U proxyObserver = createInternalObserver(observer);

    proxyObservers.put(observer, proxyObserver);
    observable.addObserver(proxyObserver);
  }

  /**
   * Removes the specified external observer by retrieving its associated internal
   * proxy observer and deregistering it from the underlying observable.
   *
   * @param observer the external observer to be removed.
   */
  public void removeObserver(T observer) {
    Optional.ofNullable(proxyObservers.remove(observer))
        .ifPresent(this.observable::removeObserver);
  }

  /**
   * Converts an external observer of type {@code T} into an internal observer of
   * type {@code U} suitable for registration with the underlying observable.
   *
   * @param observer the external observer to convert.
   * @return the internal observer instance corresponding to the provided external
   *         observer.
   */
  protected abstract U createInternalObserver(T observer);
}
