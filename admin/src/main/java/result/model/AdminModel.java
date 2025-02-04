package result.model;

import java.util.List;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public interface AdminModel {
  List<TimeDTO> getAllTimes();

  List<ParticipantDTO> getAllParticipants();

  void addListener(AdminView view);

  void removeListener(AdminView view);
}
