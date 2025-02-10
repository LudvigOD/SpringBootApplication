package admin.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import result.dto.CompetitorDTO;
import shared.dto.ParticipantDTO;

public class TestResultDTO {
  private final ParticipantDTO participant = new ParticipantDTO("01", "Alice");

  @Nested
  @DisplayName("getTimesForStation")
  class GetTimesForStationTests {
    @Test
    @DisplayName("It should return times for a station")
    public void testGetTimesForStation() {
      Map<Integer, List<Instant>> times = new HashMap<>();
      times.put(0, List.of(Instant.ofEpochSecond(123), Instant.ofEpochSecond(789)));
      times.put(1, List.of(Instant.ofEpochSecond(456)));

      CompetitorDTO competitor = new CompetitorDTO(participant, times);

      assertEquals(times.get(0), competitor.getTimesForStation(0));
      assertEquals(times.get(1), competitor.getTimesForStation(1));
    }

    @Test
    @DisplayName("It should return an empty list for a station with no times")
    public void testGetTimesForStationEmpty() {
      CompetitorDTO competitor = new CompetitorDTO(participant, new HashMap<>());

      assertEquals(0, competitor.getTimesForStation(0).size());
    }
  }

  @Nested
  @DisplayName("getOnlyTimeForStation")
  class GetOnlyTimeForStationTests {
    @Test
    @DisplayName("It should return the only time for a station")
    public void testGetOnlyTimeForStation() {
      Map<Integer, List<Instant>> times = new HashMap<>();
      times.put(0, List.of(Instant.ofEpochSecond(123)));

      CompetitorDTO competitor = new CompetitorDTO(participant, times);

      assertEquals(Instant.ofEpochSecond(123), competitor.getOnlyTimeForStation(0).get());
    }

    @Test
    @DisplayName("It should return an empty optional for a station with no times")
    public void testGetOnlyTimeForStationEmpty() {
      CompetitorDTO competitor = new CompetitorDTO(participant, new HashMap<>());

      assertEquals(false, competitor.getOnlyTimeForStation(0).isPresent());
    }

    @Test
    @DisplayName("It should return an empty optional for a station with multiple times")
    public void testGetOnlyTimeForStationMultiple() {
      Map<Integer, List<Instant>> times = new HashMap<>();
      times.put(0, List.of(Instant.ofEpochSecond(123), Instant.ofEpochSecond(456)));

      CompetitorDTO competitor = new CompetitorDTO(participant, times);

      assertEquals(false, competitor.getOnlyTimeForStation(0).isPresent());
    }
  }

  @Nested
  @DisplayName("getTotalTime")
  class GetTotalTimeTests {
    @Test
    @DisplayName("It should return the total time")
    public void testGetTotalTime() {
      Map<Integer, List<Instant>> times = new HashMap<>();
      times.put(0, List.of(Instant.ofEpochSecond(123)));
      times.put(1, List.of(Instant.ofEpochSecond(456)));

      CompetitorDTO competitor = new CompetitorDTO(participant, times);

      assertEquals(Duration.ofSeconds(333), competitor.getTotalTime().get());
    }

    @Test
    @DisplayName("It should return an empty optional for missing times")
    public void testGetTotalTimeMissing() {
      CompetitorDTO competitor = new CompetitorDTO(participant, new HashMap<>());

      assertEquals(false, competitor.getTotalTime().isPresent());
    }

    @Test
    @DisplayName("It should return an empty optional for multiple times")
    public void testGetTotalTimeMultiple() {
      Map<Integer, List<Instant>> times = new HashMap<>();
      times.put(0, List.of(Instant.ofEpochSecond(123), Instant.ofEpochSecond(456)));
      times.put(1, List.of(Instant.ofEpochSecond(789)));

      CompetitorDTO competitor = new CompetitorDTO(participant, times);

      assertEquals(false, competitor.getTotalTime().isPresent());
    }
  }
}
