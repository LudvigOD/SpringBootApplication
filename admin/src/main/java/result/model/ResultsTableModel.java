package result.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import result.dto.ResultDTO;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class ResultsTableModel extends AbstractTableModel implements AdminModelObserver {
  private final CompetitorsCalculator competitorsCalculator;
  private final ResultsCalculator resultsCalculator;
  private final String[] columnNames = { "Placering", "Startnummer", "Namn", "Total Tid" };
  private List<ResultDTO> results = new ArrayList<>();

  public ResultsTableModel() {
    this(new CompetitorsCalculator(), new ResultsCalculator());
  }

  public ResultsTableModel(CompetitorsCalculator competitorsCalculator, ResultsCalculator resultsCalculator) {
    this.competitorsCalculator = competitorsCalculator;
    this.resultsCalculator = resultsCalculator;
  }

  @Override
  public int getRowCount() {
    return results.size();
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
    ResultDTO result = results.get(rowIndex);

    switch (columnIndex) {
      case 0:
        // Place: Optional<Integer>
        return result.getPlace();
      case 1:
        // Start number: String
        return result.getCompetitor().getParticipant().getStartNbr();
      case 2:
        // Name: String
        return result.getCompetitor().getParticipant().getName();
      case 3:
        // Total time: Optional<Duration>
        return result.getCompetitor().getTotalTime();
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
      results = resultsCalculator.aggregateResultsByCompetitor(
          competitorsCalculator.aggregateTimesByParticipant(times, participants));

      fireTableDataChanged();
    });
  }
}
