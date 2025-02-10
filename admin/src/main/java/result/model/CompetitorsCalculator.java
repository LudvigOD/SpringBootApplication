package result.model;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import result.dto.CompetitorDTO;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class CompetitorsCalculator {
  /**
   * Groups the times by participant and returns a list of CompetitorDTOs.
   */
  public List<CompetitorDTO> aggregateTimesByParticipant(List<TimeDTO> times, List<ParticipantDTO> participants) {
    Map<String, List<TimeDTO>> timesPerParticipant = times.stream()
        .collect(Collectors.groupingBy(TimeDTO::getStartNbr));

    return participants.stream()
        .map(participant -> createCompetitor(participant, timesPerParticipant))
        .sorted(Comparator.comparing(c -> c.getParticipant().getStartNbr()))
        .toList();
  }

  private CompetitorDTO createCompetitor(ParticipantDTO participant, Map<String, List<TimeDTO>> timesPerParticipant) {
    Map<Integer, List<Instant>> timesForParticipant = timesPerParticipant
        .getOrDefault(participant.getStartNbr(), List.of()).stream()
        .collect(Collectors.groupingBy(
            TimeDTO::getStationId,
            Collectors.mapping(TimeDTO::getTime, Collectors.toList())));

    return new CompetitorDTO(participant, timesForParticipant);
  }
}
