package result.view;

import result.util.Competitor;
import shared.dto.TimeDTO;

public interface AdminView {
  void onTimeAdded(TimeDTO time, Competitor competitor);
}
