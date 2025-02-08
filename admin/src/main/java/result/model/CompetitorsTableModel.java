package result.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import result.dto.CompetitorDTO;
import shared.dto.ParticipantDTO;
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
  public Object getValueAt(int rowIndex, int columnIndex) {
    CompetitorDTO competitor = competitors.get(rowIndex);

    switch (columnIndex) {
      case 0:
        // Start number: String
        return competitor.getParticipant().getStartNbr();
      case 1:
        // Name: String
        return competitor.getParticipant().getName();
      case 2:
        // Start time: Optional<Instant>
        return competitor.getOnlyTimeForStation(0);
      case 3:
        // Finish time: Optional<Instant>
        return competitor.getOnlyTimeForStation(1);
      case 4:
        // Total time: Optional<Duration>
        return competitor.getTotalTime();
      default:
        return null;
    }
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public void onDataUpdated(List<TimeDTO> times, List<ParticipantDTO> participants) {
    SwingUtilities.invokeLater(() -> {
      competitors = competitorsCalculator.aggregateTimesByParticipant(times, participants);

      fireTableDataChanged();
    });
  }
}
