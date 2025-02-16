package result.model;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import result.dto.CompetitorDTO;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

/**
 * Provides functionality to aggregate competitor data from time and participant records.
 *
 * <p>
 * This class processes lists of {@link TimeDTO} and {@link ParticipantDTO} objects to produce
 * a sorted list of {@link CompetitorDTO} objects. Each competitor is constructed by grouping the
 * corresponding time records by station.
 * </p>
 */
public class CompetitorsCalculator {

  /**
   * Aggregates competitor information by matching time records with participant details.
   *
   * <p>
   * Time records are grouped by the participant's start number, and each participant is mapped
   * to a {@link CompetitorDTO} containing their corresponding times organised by station.
   * The resulting list is sorted by the participant's start number.
   * </p>
   *
   * @param times        the list of time data transfer objects to be aggregated
   * @param participants the list of participant data transfer objects
   * @return a sorted list of {@link CompetitorDTO} objects representing the aggregated competitor data
   */
  public List<CompetitorDTO> aggregateCompetitors(List<TimeDTO> times, List<ParticipantDTO> participants) {
    Map<String, List<TimeDTO>> timesPerParticipant = times.stream()
        .collect(Collectors.groupingBy(TimeDTO::getStartNbr));

    return participants.stream()
        .map(participant -> createCompetitor(participant, timesPerParticipant))
        .sorted(Comparator.comparing(c -> c.getParticipant().getStartNbr()))
        .toList();
  }

  /**
   * Creates a {@link CompetitorDTO} for a given participant using the grouped time records.
   *
   * <p>
   * The method retrieves the list of time records associated with the participant's start number.
   * These records are further grouped by station ID, mapping each station to a list of time
   * instants.
   * </p>
   *
   * @param participant         the participant for whom the competitor data is to be created
   * @param timesPerParticipant a map of start numbers to lists of time records
   * @return a {@link CompetitorDTO} containing the participant's details and their grouped times
   */
  private CompetitorDTO createCompetitor(ParticipantDTO participant, Map<String, List<TimeDTO>> timesPerParticipant) {
    Map<Integer, List<Instant>> timesForParticipant = timesPerParticipant
        .getOrDefault(participant.getStartNbr(), List.of()).stream()
        .collect(Collectors.groupingBy(
            TimeDTO::getStationId,
            Collectors.mapping(TimeDTO::getTime, Collectors.toList())));

    return new CompetitorDTO(participant, timesForParticipant);
  }
}
