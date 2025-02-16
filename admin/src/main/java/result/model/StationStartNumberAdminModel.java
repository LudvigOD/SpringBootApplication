package result.model;

import java.util.List;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

/**
 * Provides only those Times whose Station ID and Start Number match the given
 * values.
 */
public class StationStartNumberAdminModel extends FilteredAdminModel {
  private final int stationId;
  private final String startNumber;

  public StationStartNumberAdminModel(AdminModel adminModel, int stationId, String startNumber) {
    super(adminModel);
    this.stationId = stationId;
    this.startNumber = startNumber;
  }

  @Override
  protected boolean filterTime(TimeDTO time, List<ParticipantDTO> participants) {
    return time.getStationId() == stationId && time.getStartNbr().equals(startNumber);
  }
}
