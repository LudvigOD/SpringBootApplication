package register.view;

import register.util.TimeTuple;

/*
 * A simple observer pattern interface. The view must implement this interface
 * and register itself with the model to be notified of changes in the model.
 */
public interface RegisterView {

 

  /**
   * Notifies the view that a time was registered.
   * 
   * @param timeTuple
   */
  void timeWasRegistered();

}
