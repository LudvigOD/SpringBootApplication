package result.model;

import java.util.List;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public interface AdminModelObserver {
  void onDataUpdated(List<TimeDTO> times, List<ParticipantDTO> participants);
}
