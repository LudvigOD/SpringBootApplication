package result.view;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import result.dto.CompetitorDTO;
import result.model.AdminModelObserver;
import result.model.CompetitorsCalculator;
import shared.dto.RaceConfigurationDTO;
import shared.dto.StationDTO;
import shared.dto.TimeDTO;

public class CompetitorsTableModel extends AbstractTableModel implements AdminModelObserver {
  private final CompetitorsCalculator competitorsCalculator;
  private List<CompetitorDTO> competitors = new ArrayList<>();
  private List<StationDTO> stations = new ArrayList<>();

  public CompetitorsTableModel() {
    this(new CompetitorsCalculator());
  }

  public CompetitorsTableModel(CompetitorsCalculator competitorsCalculator) {
    this.competitorsCalculator = competitorsCalculator;
  }

  @Override
  public int getRowCount() {
    return competitors.size();
  }

  @Override
  public int getColumnCount() {
    return 3 + Math.max(0, 2 * stations.size() - 1);
  }

  @Override
  public String getColumnName(int column) {
    switch (column) {
      case 0:
        return "Startnummer";
      case 1:
        return "Namn";
      case 2:
        return "Totaltid";
      default:
        return stations.get(getStationIndex(column)).getName();
    }
  }

  @Override
  public Object getValueAt(int row, int column) {
    CompetitorDTO competitor = competitors.get(row);

    switch (column) {
      case 0:
        // Start number: String
        return competitor.getParticipant().getStartNbr();
      case 1:
        // Name: String
        return competitor.getParticipant().getName();
      case 2:
        // Total time: Optional<Duration>
        return competitor.getTotalTime();
      default:
        long stationId = getStationId(column);

        if (isDuration(column)) {
          // Station Duration: Optional<Duration>
          long prevStationId = getStationId(column - 1);

          return competitor.getOnlyTimeForStation(stationId).flatMap(time -> competitor
              .getOnlyTimeForStation(prevStationId).map(prevTime -> Duration.between(prevTime, time)));
        } else {
          // Station Times: List<Instant>
          return competitor.getTimesForStation(stationId);
        }
    }
  }

  public long getStationId(int column) {
    return stations.get(getStationIndex(column)).getStationId();
  }

  private int getStationIndex(int column) {
    // 0 -> Startnummer
    // 1 -> Namn
    // 2 -> Totaltid (~Station 0~)
    // 3 -> Station 1 (Duration)
    // 4 -> Station 2 (Duration)
    // 5 -> Station 3 (Duration)
    // 6 -> Station 0 (Instant)
    // 7 -> Station 1 (Instant)
    // 8 -> Station 2 (Instant)
    // 9 -> Station 3 (Instant)
    return (column - 2) % stations.size();
  }

  public boolean isDuration(int column) {
    return column - 2 < stations.size();
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public void onDataUpdated(RaceConfigurationDTO raceConfig, List<TimeDTO> times) {
    SwingUtilities.invokeLater(() -> {
      this.competitors = competitorsCalculator.aggregateCompetitors(raceConfig.getStations(), times,
          raceConfig.getParticipants());
      this.stations = raceConfig.getStations();

      fireTableDataChanged();
      fireTableStructureChanged();
    });
  }
}
