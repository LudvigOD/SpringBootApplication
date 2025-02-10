package admin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import result.dto.CompetitorDTO;
import result.model.CompetitorsCalculator;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class TestCompetitorsCalculator {
  private CompetitorsCalculator calculator;

  @BeforeEach
  public void setUp() {
    calculator = new CompetitorsCalculator();
  }

  @Test
  @DisplayName("It should group times by participant.")
  public void testGroupTimesByParticipant() {
    List<TimeDTO> times = List.of(
        new TimeDTO(0, "01", Instant.ofEpochSecond(123)),
        new TimeDTO(1, "01", Instant.ofEpochSecond(456)),
        new TimeDTO(0, "02", Instant.ofEpochSecond(789)));

    List<ParticipantDTO> participants = List.of(
        new ParticipantDTO("01", "Alice"),
        new ParticipantDTO("02", "Bob"));

    List<CompetitorDTO> competitors = calculator.aggregateTimesByParticipant(times, participants);

    assertEquals(2, competitors.size());

    assertEquals("01", competitors.get(0).getParticipant().getStartNbr());
    assertEquals(List.of(Instant.ofEpochSecond(123)), competitors.get(0).getTimesForStation(0));
    assertEquals(List.of(Instant.ofEpochSecond(456)), competitors.get(0).getTimesForStation(1));

    assertEquals("02", competitors.get(1).getParticipant().getStartNbr());
    assertEquals(List.of(Instant.ofEpochSecond(789)), competitors.get(1).getTimesForStation(0));
  }

  @Test
  @DisplayName("It should handle duplicate times.")
  public void testDuplicate() {
    List<TimeDTO> times = List.of(
        new TimeDTO(0, "01", Instant.ofEpochSecond(123)),
        new TimeDTO(1, "01", Instant.ofEpochSecond(456)),
        new TimeDTO(0, "01", Instant.ofEpochSecond(789)));

    List<ParticipantDTO> participants = List.of(
        new ParticipantDTO("01", "Alice"));

    List<CompetitorDTO> competitors = calculator.aggregateTimesByParticipant(times, participants);

    assertEquals(1, competitors.size());

    assertEquals(List.of(Instant.ofEpochSecond(123), Instant.ofEpochSecond(789)),
        competitors.get(0).getTimesForStation(0));
    assertEquals(List.of(Instant.ofEpochSecond(456)), competitors.get(0).getTimesForStation(1));
  }

  @Test
  @DisplayName("It should include participants without times.")
  public void testMissingTime() {
    List<TimeDTO> times = List.of();

    List<ParticipantDTO> participants = List.of(
        new ParticipantDTO("01", "Alice"));

    List<CompetitorDTO> competitors = calculator.aggregateTimesByParticipant(times, participants);

    assertEquals(1, competitors.size());

    assertEquals("01", competitors.get(0).getParticipant().getStartNbr());
    assertEquals(List.of(), competitors.get(0).getTimesForStation(0));
  }

  @Test
  @DisplayName("It should handle missing participants.")
  public void testMissingParticipant() {
    List<TimeDTO> times = List.of(
        new TimeDTO(0, "01", Instant.ofEpochSecond(123)));

    List<ParticipantDTO> participants = List.of();

    List<CompetitorDTO> competitors = calculator.aggregateTimesByParticipant(times, participants);

    assertEquals(0, competitors.size());
  }

  @Test
  @DisplayName("It should sort competitors by start number.")
  public void testSort() {
    List<TimeDTO> times = List.of(
        new TimeDTO(0, "10", Instant.ofEpochSecond(123)),
        new TimeDTO(1, "01", Instant.ofEpochSecond(456)),
        new TimeDTO(0, "01", Instant.ofEpochSecond(789)));

    List<ParticipantDTO> participants = List.of(
        new ParticipantDTO("10", "Charlie"),
        new ParticipantDTO("01", "Alice"),
        new ParticipantDTO("02", "Bob"));

    List<CompetitorDTO> competitors = calculator.aggregateTimesByParticipant(times, participants);

    assertEquals(3, competitors.size());

    assertEquals("01", competitors.get(0).getParticipant().getStartNbr());
    assertEquals("02", competitors.get(1).getParticipant().getStartNbr());
    assertEquals("10", competitors.get(2).getParticipant().getStartNbr());
  }
}
