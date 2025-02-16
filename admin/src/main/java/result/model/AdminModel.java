package result.model;

import java.util.List;

import result.util.Observable;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public interface AdminModel extends Observable<AdminModelObserver> {
  List<TimeDTO> getAllTimes();

  List<ParticipantDTO> getAllParticipants();

  public void updateTime(int raceID, TimeDTO timeDTO);

  public void sendPostRequest(ParticipantDTO dto, int raceId);
}
