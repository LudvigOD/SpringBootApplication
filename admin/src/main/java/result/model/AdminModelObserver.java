package result.model;

import java.util.List;

import shared.dto.RaceConfigurationDTO;
import shared.dto.TimeDTO;

public interface AdminModelObserver {
  void onDataUpdated(RaceConfigurationDTO raceConfiguration, List<TimeDTO> times);
}
