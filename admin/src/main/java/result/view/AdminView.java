package result.view;

import result.dto.ResultDTO;
import shared.dto.TimeDTO;

public interface AdminView {
  void onTimeAdded(TimeDTO time, ResultDTO result);
}
