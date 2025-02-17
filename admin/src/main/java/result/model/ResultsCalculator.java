package result.model;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import result.dto.CompetitorDTO;
import result.dto.ResultDTO;

/**
 * Calculates race results by aggregating competitor data.
 *
 * <p>
 * This class processes a list of {@link CompetitorDTO} objects, sorting them based on their
 * total race time and assigning places to those who have finished. The place for each competitor
 * is determined by their position in the sorted list, adjusted to a 1-based index.
 * </p>
 */
public class ResultsCalculator {

  /**
   * Aggregates results from a list of competitors.
   *
   * <p>
   * Competitors are sorted in ascending order of their total time, with those not having a total
   * time (i.e. not finished) considered as having the maximum possible duration. For each competitor,
   * a {@link ResultDTO} is created, where the competitor's place is set if they have finished.
   * </p>
   *
   * @param competitors the list of competitors to be evaluated
   * @return a list of {@link ResultDTO} objects representing the aggregated results
   */
  public List<ResultDTO> aggregateResults(List<CompetitorDTO> competitors) {
    List<CompetitorDTO> sortedCompetitors = competitors.stream()
        .sorted(Comparator.comparing(c -> c.getTotalTime().orElse(Duration.ofSeconds(Long.MAX_VALUE))))
        .toList();

    return IntStream.range(0, sortedCompetitors.size()).mapToObj(i -> {
      CompetitorDTO competitor = sortedCompetitors.get(i);
      // For competitors that have finished, their place is their index in the sorted list + 1.
      return new ResultDTO(competitor, competitor.getTotalTime().map(t -> i + 1));
    }).toList();
  }
}
