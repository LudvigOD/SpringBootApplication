package result.model;

import java.util.List;

import result.util.ProxyObservable;
import shared.dto.ParticipantDTO;
import shared.dto.RaceConfigurationDTO;
import shared.dto.TimeDTO;

public abstract class FilteredAdminModel extends ProxyObservable<AdminModelObserver, AdminModelObserver, AdminModel>
    implements AdminModel {
  private final AdminModel adminModel;

  public FilteredAdminModel(AdminModel model) {
    super(model);
    this.adminModel = model;
  }

  @Override
  public void updateTime(TimeDTO timeDTO) {
    adminModel.updateTime(timeDTO);
  }

  @Override
  public void createParticipant(ParticipantDTO dto) {
    adminModel.createParticipant(dto);
  }

  @Override
  protected AdminModelObserver createInternalObserver(AdminModelObserver observer) {
    return new AdminModelObserver() {
      @Override
      public void onDataUpdated(RaceConfigurationDTO raceConfig, List<TimeDTO> times) {
        observer.onDataUpdated(raceConfig, times.stream().filter(time -> filterTime(time, raceConfig)).toList());
      }
    };
  }

  protected boolean filterTime(TimeDTO time, RaceConfigurationDTO raceConfig) {
    return true;
  }
}
