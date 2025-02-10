package result.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import result.dto.CompetitorDTO;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class TimesTableModel extends AbstractTableModel implements AdminModelObserver {
  private final CompetitorsCalculator competitorsCalculator;
  private final String[] columnNames = { "Station", "Nr.", "Tid" };
  private List<TimeDTO> times = new ArrayList<>();
  private Map<String, CompetitorDTO> competitors = new HashMap<>();
  private final AdminModel adminModel;

  public TimesTableModel(AdminModel adminModel) {
    this(new CompetitorsCalculator(), adminModel);
  }

  public TimesTableModel(CompetitorsCalculator competitorsCalculator, AdminModel adminModel) {
    this.competitorsCalculator = competitorsCalculator;
    this.adminModel = adminModel;
  }

  @Override
  public int getRowCount() {
    return times.size();
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
    TimeDTO time = times.get(rowIndex);

    switch (columnIndex) {
      case 0:
        // Station ID: int
        return time.getStationId();
      case 1:
        // Start number: String
        return time.getStartNbr();
      case 2:
        // Time: Instant
        return time.getTime();
      default:
        return null;
    }
  }

  @Override
  public void setValueAt(Object value, int row, int col) {
    TimeDTO time = times.get(row);

    switch (col) {
      case 1:
        // Start number: String
        String startNbr = (String) value;

        time.setStartNbr(startNbr);

        adminModel.updateTime(1, time);
        break;
    }
  }

  public boolean isDuplicateTime(int row) {
    TimeDTO time = times.get(row);
    Optional<CompetitorDTO> competitor = Optional.ofNullable(competitors.get(time.getStartNbr()));
    Optional<List<Instant>> competitorTimes = competitor.map(c -> c.getTimesForStation(time.getStationId()));

    return competitorTimes.map(c -> c.size() > 1).orElse(false);
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return column == 1;
  }

  public void onDataUpdated(List<TimeDTO> times, List<ParticipantDTO> participants) {
    SwingUtilities.invokeLater(() -> {
      this.times = times;
      competitors = competitorsCalculator.aggregateTimesByParticipant(times, participants).stream().collect(
          HashMap::new, (map, competitor) -> map.put(competitor.getParticipant().getStartNbr(), competitor),
          HashMap::putAll);

      fireTableDataChanged();
    });
  }
}
