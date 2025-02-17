package result.model;

import shared.dto.RaceConfigurationDTO;
import shared.dto.TimeDTO;

/**
 * Provides only those Times whose Station ID and Start Number match the given
 * values.
 */
public class StationStartNumberAdminModel extends FilteredAdminModel {
  private final int stationId;
  private final String startNumber;
  private final AdminModel adminModel;

  public StationStartNumberAdminModel(AdminModel adminModel, int stationId, String startNumber) {
    super(adminModel);
    this.stationId = stationId;
    this.startNumber = startNumber;
    this.adminModel = adminModel;
  }

  @Override
  protected boolean filterTime(TimeDTO time, RaceConfigurationDTO raceConfig) {
    return time.getStationId() == stationId && time.getStartNbr().equals(startNumber);
  }

  @Override
  public void setRaceID(int raceID) {
    adminModel.setRaceID(raceID);
  }
}
