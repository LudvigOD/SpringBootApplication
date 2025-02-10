package result.model;

import java.util.List;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public interface AdminModel {
  void addListener(AdminModelObserver observer);

  void removeListener(AdminModelObserver observer);

  List<TimeDTO> getAllTimes();

  List<ParticipantDTO> getAllParticipants();

  public void updateTime(int raceID, TimeDTO timeDTO);

  public void sendPostRequest(ParticipantDTO dto, int raceId);
}
