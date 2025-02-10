package result.dto;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import shared.dto.ParticipantDTO;

/**
 * A Competitor DTO contains information about a specific participant together with
 * their registered times per station.
 */
public class CompetitorDTO {
  private ParticipantDTO participant;
  private Map<Integer, List<Instant>> timesPerStation;

  public CompetitorDTO(ParticipantDTO participant, Map<Integer, List<Instant>> timesPerStation) {
    this.participant = participant;
    this.timesPerStation = timesPerStation;
  }

  public ParticipantDTO getParticipant() {
    return participant;
  }

  public Map<Integer, List<Instant>> getTimesPerStation() {
    return timesPerStation;
  }

  public List<Instant> getTimesForStation(int stationId) {
    return timesPerStation.getOrDefault(stationId, List.of());
  }

  public Optional<Instant> getOnlyTimeForStation(int stationId) {
    List<Instant> times = getTimesForStation(stationId);

    return times.size() == 1
        ? Optional.of(times.get(0))
        : Optional.empty();
  }

  public Optional<Duration> getTotalTime() {
    return getOnlyTimeForStation(0)
        .flatMap(startTime -> getOnlyTimeForStation(1).map(finishTime -> Duration.between(startTime, finishTime)));
  }

  @Override
  public String toString() {
    return "CompetitorDTO [participant=" + participant + ", timesPerStation=" + timesPerStation + "]";
  }
}
