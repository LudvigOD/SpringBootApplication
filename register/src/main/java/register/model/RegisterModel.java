package register.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import register.util.TimeTuple;
import register.view.RegisterView;
import shared.dto.TimeDTO;

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

  List<TimeDTO> syncReloadTimes(Optional<Integer> stationId);

  void asyncReloadTimes(Consumer<List<TimeDTO>> responseHandler, int stationId);

}
