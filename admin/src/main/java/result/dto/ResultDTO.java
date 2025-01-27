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
}
