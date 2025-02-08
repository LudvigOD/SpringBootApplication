package result.model;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import result.dto.CompetitorDTO;
import result.dto.ResultDTO;

public class ResultsCalculator {
  /**
   * Aggregates the results by competitor and returns a list of ResultDTOs.
   */
  public List<ResultDTO> aggregateResultsByCompetitor(List<CompetitorDTO> competitors) {
    List<CompetitorDTO> sortedCompetitors = competitors.stream()
        .sorted(Comparator.comparing(c -> c.getTotalTime().orElse(Duration.ofSeconds(Long.MAX_VALUE))))
        .toList();

    return IntStream.range(0, sortedCompetitors.size()).mapToObj(i -> {
      CompetitorDTO competitor = sortedCompetitors.get(i);

      // For those competitors that have finished, their place is their index in the
      // sorted list + 1 (because lists are 0-indexed, but we count from 1).
      return new ResultDTO(competitor, competitor.getTotalTime().map(t -> i + 1));
    }).toList();
  }
}
