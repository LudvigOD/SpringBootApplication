package result.dto;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class ResultDTO {
  public String startNbr;
  public String name;
  public Optional<Instant> startTime;
  public Optional<Instant> finishTime;
  public Optional<Duration> totalDuration;

  public ResultDTO(String startNbr) {
    this.startNbr = startNbr;
    name = "";
  }

  public String getStartNbr() {
    return startNbr;
  }

  public String getName() {
    return name;
  }

  public Optional<Instant> getStartTime(){
    return startTime;
  }

  public Optional<Instant> getFinishTime() {
    return finishTime;
  } 

  public Optional<Duration> getTotal() {
    return totalDuration;
  }
}

