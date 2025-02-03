package result.dto;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class ResultDTO {
  private Optional<Integer> place;
  private String startNbr;
  private String name;
  private Optional<Instant> startTime;
  private Optional<Instant> finishTime;
  private Optional<Duration> totalDuration;

  public ResultDTO(Optional<Integer> place, String startNbr, String name,
      Optional<Instant> startTime, Optional<Instant> finishTime, Optional<Duration> totalDuration) {
    this.place = place;
    this.startNbr = startNbr;
    this.name = name;
    this.startTime = startTime;
    this.finishTime = finishTime;
    this.totalDuration = totalDuration;
  }

  public Optional<Integer> getPlace() {
    return place;
  }

  public String getStartNbr() {
    return startNbr;
  }

  public String getName() {
    return name;
  }

  public Optional<Instant> getStartTime() {
    return startTime;
  }

  public Optional<Instant> getFinishTime() {
    return finishTime;
  }

  public Optional<Duration> getTotalTime() {
    return totalDuration;
  }

  public void setPlace(Optional<Integer> place) {
    this.place = place;
  }

  @Override
  public String toString() {
    return String.format("ResultDTO{place=%s, startNbr='%s', name='%s', startTime=%s, finishTime=%s, totalDuration=%s}",
        place, startNbr, name, startTime, finishTime, totalDuration);
  }
}
