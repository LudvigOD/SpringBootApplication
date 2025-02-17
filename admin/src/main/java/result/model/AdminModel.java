package result.model;

import result.util.Observable;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public interface AdminModel extends Observable<AdminModelObserver> {
  void setRaceID(int raceID);

  void updateTime(TimeDTO timeDTO);

  void createParticipant(ParticipantDTO dto);
}
