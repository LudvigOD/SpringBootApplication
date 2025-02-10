/* package admin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import result.dto.ResultDTO;
import result.model.ResultsTableModel;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class TestResultsTableModel {
  @Test
  public void testFormatTimesToResults() {
    List<TimeDTO> times = List.of(
        new TimeDTO(0, "01", Instant.ofEpochSecond(123), Long.valueOf(1)),
        new TimeDTO(1, "01", Instant.ofEpochSecond(456), Long.valueOf(2)),
        new TimeDTO(0, "02", Instant.ofEpochSecond(789), Long.valueOf(3)));

    List<ParticipantDTO> participants = List.of(
        new ParticipantDTO("01", "Alice"),
        new ParticipantDTO("02", "Bob"));

    List<ResultDTO> results = new ResultsTableModel().formatTimesToResults(times, participants);

    assertEquals(2, results.size());

    assertEquals(1, results.get(0).getPlace().get());
    assertEquals("01", results.get(0).getStartNbr());
    assertEquals("Alice", results.get(0).getName());
    assertEquals(333, results.get(0).getTotalTime().get().toSeconds());

    assertEquals(false, results.get(1).getPlace().isPresent());
    assertEquals("02", results.get(1).getStartNbr());
    assertEquals("Bob", results.get(1).getName());
    assertEquals(false, results.get(1).getTotalTime().isPresent());
  }

  @Test
  public void testFormatTimesToResultsDuplicate() {
    List<TimeDTO> times = List.of(
        new TimeDTO(0, "01", Instant.ofEpochSecond(123), Long.valueOf(1)),
        new TimeDTO(1, "01", Instant.ofEpochSecond(456), Long.valueOf(2)),
        new TimeDTO(0, "01", Instant.ofEpochSecond(789), Long.valueOf(3)));

    List<ParticipantDTO> participants = List.of(
        new ParticipantDTO("01", "Alice"));

    List<ResultDTO> results = new ResultsTableModel().formatTimesToResults(times, participants);

    assertEquals(1, results.size());

    assertEquals(false, results.get(0).getPlace().isPresent());
    assertEquals("01", results.get(0).getStartNbr());
    assertEquals("Alice", results.get(0).getName());
    assertEquals(false, results.get(0).getTotalTime().isPresent());
  }

  @Test
  public void testFormatTimesToResultsMissing() {
    List<TimeDTO> times = List.of(
        new TimeDTO(0, "01", Instant.ofEpochSecond(123), Long.valueOf(1)),
        new TimeDTO(0, "02", Instant.ofEpochSecond(456), Long.valueOf(2)));

    List<ParticipantDTO> participants = List.of(
        new ParticipantDTO("01", "Alice"),
        new ParticipantDTO("02", "Bob"));

    List<ResultDTO> results = new ResultsTableModel().formatTimesToResults(times, participants);

    assertEquals(2, results.size());

    assertEquals(false, results.get(0).getPlace().isPresent());
    assertEquals("01", results.get(0).getStartNbr());
    assertEquals("Alice", results.get(0).getName());
    assertEquals(false, results.get(0).getTotalTime().isPresent());

    assertEquals(false, results.get(1).getPlace().isPresent());
    assertEquals("02", results.get(1).getStartNbr());
    assertEquals("Bob", results.get(1).getName());
    assertEquals(false, results.get(1).getTotalTime().isPresent());
  }

  @Test
  public void testFormatTimesToResultsOrderPlacement() {
    List<TimeDTO> times = List.of(
        new TimeDTO(0, "01", Instant.ofEpochSecond(123), Long.valueOf(1)),
        new TimeDTO(1, "01", Instant.ofEpochSecond(456), Long.valueOf(2)),
        new TimeDTO(0, "02", Instant.ofEpochSecond(789), Long.valueOf(3)),
        new TimeDTO(1, "02", Instant.ofEpochSecond(101112), Long.valueOf(4)));

    List<ParticipantDTO> participants = List.of(
        new ParticipantDTO("01", "Alice"),
        new ParticipantDTO("02", "Bob"));

    List<ResultDTO> results = new ResultsTableModel().formatTimesToResults(times, participants);

    assertEquals(2, results.size());

    assertEquals(1, results.get(0).getPlace().get());
    assertEquals("01", results.get(0).getStartNbr());
    assertEquals("Alice", results.get(0).getName());
    assertEquals(333, results.get(0).getTotalTime().get().toSeconds());

    assertEquals(2, results.get(1).getPlace().get());
    assertEquals("02", results.get(1).getStartNbr());
    assertEquals("Bob", results.get(1).getName());
    assertEquals(100323, results.get(1).getTotalTime().get().toSeconds());
  }

  @Test
  public void testFormatTimesToResultsEmpty() {
    List<TimeDTO> times = List.of();
    List<ParticipantDTO> participants = List.of();

    List<ResultDTO> results = new ResultsTableModel().formatTimesToResults(times, participants);

    assertEquals(0, results.size());
  }

  @Test
  public void testFormatTimesToResultsEmptyParticipants() {
    List<TimeDTO> times = List.of(
        new TimeDTO(0, "01", Instant.ofEpochSecond(123), Long.valueOf(1)),
        new TimeDTO(0, "02", Instant.ofEpochSecond(456), Long.valueOf(2)));

    List<ParticipantDTO> participants = List.of();

    List<ResultDTO> results = new ResultsTableModel().formatTimesToResults(times, participants);

    assertEquals(0, results.size());
  }

}
 */