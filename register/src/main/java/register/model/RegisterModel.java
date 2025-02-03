package register.model;

import register.util.TimeTuple;
import register.view.RegisterView;

public interface RegisterModel {

  void addListener(RegisterView view);

  void removeListener(RegisterView view);

  /**
   * Registers a time for a start number using the current time.
   *
   * @param startNbr
   */
  void registerTime(String startNbr, Integer station);


  /**
   * Edits a registered time.
   * @param timeTuple
   */
  void editRegisteredTime(String startNbr, TimeTuple timeTuple);

}
