package result.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import result.model.AdminModel;
import result.model.AdminModelObserver;
import shared.dto.RaceConfigurationDTO;
import shared.dto.StationDTO;
import shared.dto.TimeDTO;

public class TimesTableModel extends AbstractTableModel implements AdminModelObserver {
  private final String[] columnNames = { "Station", "Nr.", "Tid" };
  private List<TimeDTO> times = new ArrayList<>();
  /**
   * Key: Station ID, Value: StationDTO
   */
  private Map<Long, StationDTO> stations = new HashMap<>();
  private final AdminModel model;

  public TimesTableModel(AdminModel model) {
    this.model = model;
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
        // Station name: Optional<String>
        return Optional.ofNullable(stations.get(time.getStationId())).map(StationDTO::getName);
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

        model.updateTime(time);
        break;
    }
  }

  public boolean isDuplicateTime(int row) {
    TimeDTO time = times.get(row);

    List<TimeDTO> duplicateTimes = times.stream()
        .filter(t -> t.getStationId() == time.getStationId() && t.getStartNbr().equals(time.getStartNbr()))
        .toList();

    return duplicateTimes.size() > 1;
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return column == 1;
  }

  public void onDataUpdated(RaceConfigurationDTO raceConfig, List<TimeDTO> times) {
    SwingUtilities.invokeLater(() -> {
      this.times = times;
      this.stations = raceConfig.getStations().stream().collect(HashMap::new,
          (map, station) -> map.put(station.getStationId(), station), HashMap::putAll);

      fireTableDataChanged();
    });
  }
}
