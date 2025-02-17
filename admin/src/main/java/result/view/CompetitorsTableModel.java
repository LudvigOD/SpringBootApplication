package result.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import result.dto.CompetitorDTO;
import result.model.AdminModelObserver;
import result.model.CompetitorsCalculator;
import shared.dto.RaceConfigurationDTO;
import shared.dto.TimeDTO;

public class CompetitorsTableModel extends AbstractTableModel implements AdminModelObserver {
  private final CompetitorsCalculator competitorsCalculator;
  private final String[] columnNames = { "Startnummer", "Namn", "Start", "Mål", "Totalt" };
  private List<CompetitorDTO> competitors = new ArrayList<>();

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
    return columnNames.length;
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  @Override
  public Object getValueAt(int row, int index) {
    CompetitorDTO competitor = competitors.get(row);

    switch (index) {
      case 0:
        // Start number: String
        return competitor.getParticipant().getStartNbr();
      case 1:
        // Name: String
        return competitor.getParticipant().getName();
      case 2:
      case 3:
        // Start time: List<Instant>
        return competitor.getTimesForStation(getStationId(index));
      case 4:
        // Total time: Optional<Duration>
        return competitor.getTotalTime();
      default:
        return null;
    }
  }

  public int getStationId(int column) {
    switch (column) {
      case 2:
        return 0;
      case 3:
        return 1;
      default:
        return -1;
    }
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public void onDataUpdated(RaceConfigurationDTO raceConfig, List<TimeDTO> times) {
    SwingUtilities.invokeLater(() -> {
      this.competitors = competitorsCalculator.aggregateCompetitors(times, raceConfig.getParticipants());

      fireTableDataChanged();
    });
  }
}
