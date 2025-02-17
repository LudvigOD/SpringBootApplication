package result.model;

import java.util.List;

import result.util.ProxyObservable;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public abstract class FilteredAdminModel extends ProxyObservable<AdminModelObserver, AdminModelObserver, AdminModel>
    implements AdminModel {
  private final AdminModel adminModel;

  public FilteredAdminModel(AdminModel model) {
    super(model);
    this.adminModel = model;
  }

  @Override
  public List<TimeDTO> getAllTimes() {
    return adminModel.getAllTimes();
  }

  @Override
  public List<ParticipantDTO> getAllParticipants() {
    return adminModel.getAllParticipants();
  }

  @Override
  public void updateTime(int raceID, TimeDTO timeDTO) {
    adminModel.updateTime(raceID, timeDTO);
  }

  @Override
  public void sendPostRequest(ParticipantDTO dto, int raceId) {
    adminModel.sendPostRequest(dto, raceId);
  }

  @Override
  protected AdminModelObserver createInternalObserver(AdminModelObserver observer) {
    return new AdminModelObserver() {
      @Override
      public void onDataUpdated(List<TimeDTO> times, List<ParticipantDTO> participants) {
        observer.onDataUpdated(times.stream().filter(time -> filterTime(time, participants)).toList(),
            participants.stream().filter(participant -> filterParticipant(participant, times)).toList());
      }
    };
  }

  protected boolean filterTime(TimeDTO time, List<ParticipantDTO> participants) {
    return true;
  }

  protected boolean filterParticipant(ParticipantDTO participant, List<TimeDTO> times) {
    return true;
  }

}
