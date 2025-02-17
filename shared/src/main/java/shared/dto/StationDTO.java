package shared.dto;

public class StationDTO {
  private long stationId;
  private String name;

  public StationDTO() {
      // The automatic JSON conversion requires a default constructor
  }

  public StationDTO(long stationId, String name) {
      this.stationId = stationId;
      this.name = name;
  }

  public long getStationId() {
    return stationId;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("StationDTO{stationId='%s', name='%s'}", stationId, name);
  }
}
