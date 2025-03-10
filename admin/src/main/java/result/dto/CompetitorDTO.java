package result.dto;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import shared.dto.ParticipantDTO;

/**
 * A Competitor DTO contains information about a specific participant together
 * with their registered times per station.
 */
public class CompetitorDTO {
  private ParticipantDTO participant;
  private Map<Long, List<Instant>> timesPerStation;
  private Optional<Duration> totalTime;

  public CompetitorDTO(ParticipantDTO participant, Map<Long, List<Instant>> timesPerStation,
      Optional<Duration> totalTime) {
    this.participant = participant;
    this.timesPerStation = timesPerStation;
    this.totalTime = totalTime;
  }

  public ParticipantDTO getParticipant() {
    return participant;
  }

  public Map<Long, List<Instant>> getTimesPerStation() {
    return timesPerStation;
  }

  public List<Instant> getTimesForStation(long stationId) {
    return timesPerStation.getOrDefault(stationId, List.of());
  }

  public Optional<Instant> getOnlyTimeForStation(long stationId) {
    List<Instant> times = getTimesForStation(stationId);

    return times.size() == 1
        ? Optional.of(times.get(0))
        : Optional.empty();
  }

  public Optional<Duration> getTotalTime() {
    return this.totalTime;
  }

  @Override
  public String toString() {
    return "CompetitorDTO [participant=" + participant + ", timesPerStation=" + timesPerStation + ", totalTime="
        + totalTime + "]";
  }
}
