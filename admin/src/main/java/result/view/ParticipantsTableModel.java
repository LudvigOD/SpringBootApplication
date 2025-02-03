package result.view;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import result.dto.ResultDTO;
import result.model.AdminView;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class ParticipantsTableModel extends DefaultTableModel implements AdminView {
  public ParticipantsTableModel() {
    super(new String[] { "Startnummer", "Namn", "Start", "Mål", "Totalt" }, 0);
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public void onDataUpdated(List<TimeDTO> times, List<ParticipantDTO> participants) {
    SwingUtilities.invokeLater(() -> updateResults(times, participants));
  }

  private void updateResults(List<TimeDTO> times, List<ParticipantDTO> participants) {
    List<ResultDTO> results = formatTimesToResults(times, participants);
    setRowCount(0);

    for (ResultDTO result : results) {
      addRow(new Object[] { result.getStartNbr(), result.getName(), result.getStartTime(), result.getFinishTime(),
          result.getTotalTime() });
    }

    fireTableDataChanged();
  }

  public List<ResultDTO> formatTimesToResults(
      List<TimeDTO> times,
      List<ParticipantDTO> participants) {
    int startStationId = 0;
    int finishStationId = 1;

    Map<String, List<TimeDTO>> timesPerParticipant = times.stream()
        .collect(Collectors.groupingBy(TimeDTO::getStartNbr));

    return participants.stream().map(participant -> {
      List<TimeDTO> participantTimes = timesPerParticipant.getOrDefault(participant.getStartNbr(), List.of());

      Map<Integer, List<TimeDTO>> timesPerStation = participantTimes.stream()
          .collect(Collectors.groupingBy(TimeDTO::getStationId));

      List<TimeDTO> startTimes = timesPerStation.getOrDefault(startStationId, List.of());
      List<TimeDTO> finishTimes = timesPerStation.getOrDefault(finishStationId, List.of());

      Optional<Instant> onlyStartTime = startTimes.size() == 1
          ? Optional.of(startTimes.get(0).getTime())
          : Optional.empty();

      Optional<Instant> onlyFinishTime = finishTimes.size() == 1
          ? Optional.of(finishTimes.get(0).getTime())
          : Optional.empty();

      Optional<Duration> totalTime = onlyStartTime
          .flatMap(startTime -> onlyFinishTime.map(finishTime -> Duration.between(startTime, finishTime)));

      return new ResultDTO(
          Optional.empty(),
          participant.getStartNbr(),
          participant.getName(),
          onlyStartTime,
          onlyFinishTime,
          totalTime);
    }).toList();
  }
}
