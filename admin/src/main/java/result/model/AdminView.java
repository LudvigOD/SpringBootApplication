package result.model;

import java.util.List;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public interface AdminView {
  void onDataUpdated(List<TimeDTO> times, List<ParticipantDTO> participants);
}
