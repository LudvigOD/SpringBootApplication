package result.dto;

import java.util.Optional;

/**
 * A Result DTO extends a Competitor DTO with additional information about the
 * result of a participant in a competition.
 */
public class ResultDTO {
  private CompetitorDTO competitor;
  private Optional<Integer> place;

  public ResultDTO(CompetitorDTO competitor, Optional<Integer> place) {
    this.competitor = competitor;
    this.place = place;
  }

  public CompetitorDTO getCompetitor() {
    return competitor;
  }

  public Optional<Integer> getPlace() {
    return place;
  }

  @Override
  public String toString() {
    return "ResultDTO [competitor=" + competitor + ", place=" + place + "]";
  }
}
