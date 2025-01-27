package result.model;

import java.util.List;

import shared.dto.TimeDTO;

public interface AdminModel {

  // void addListener(AdminView view);

  // void removeListener(AdminView view);

  /**
   * Retrieves all the times from the server.
   * 
   * @param startNbr
   */
  List<TimeDTO> getParticipantTimes(String startNbr);

  /**
   * Starts the competition.
   * 
   * @param amountCompetitors
   * @param stations
   */
  void startCompetition(int amountCompetitors, int stations);

  int getNbrCompetitors();

  int getNbrStations();

  /** */

}
