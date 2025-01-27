package shared.dto;

import java.time.Instant;

import org.springframework.lang.NonNull;

public class TimeDTO {
  private Integer stationId;
  private String startNbr;

  private Instant time;

  public TimeDTO() {
    // The automatic JSON conversion requires a default constructor
  }

  public TimeDTO(
      Integer stationId,
      @NonNull String startNbr,
      Instant time) {
    this.stationId = stationId;
    this.startNbr = startNbr;
    this.time = time;
  }

  public Integer getStationId() {
    return stationId;
  }

  public String getStartNbr() {
    return startNbr;
  }

  public Instant getTime() {
    return time;
  }

  @Override
  public String toString() {
    return String.format("%s; %s; %s", stationId, startNbr, time);
  }
}
