package result.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class AdminModelFiltered implements AdminModel {
  private final AdminModel delegate;
  private final Map<AdminModelObserver, AdminModelObserver> proxyObservers = new HashMap<>();

  private final int stationId;
  private final String startNbr;

  public AdminModelFiltered(AdminModel delegate, int stationId, String startNbr) {
    this.delegate = delegate;
    this.stationId = stationId;
    this.startNbr = startNbr;
  }

  @Override
  public void addListener(AdminModelObserver observer) {
    AdminModelObserver proxyObserver = new AdminModelObserver() {
      @Override
      public void onDataUpdated(List<TimeDTO> times, List<ParticipantDTO> participants) {
        observer.onDataUpdated(times.stream().filter(AdminModelFiltered.this::filterTime).toList(),
            participants.stream().filter(AdminModelFiltered.this::filterParticipant).toList());
      }
    };

    proxyObservers.put(observer, proxyObserver);
    delegate.addListener(proxyObserver);
  }

  @Override
  public void removeListener(AdminModelObserver observer) {
    Optional.ofNullable(proxyObservers.remove(observer)).ifPresent(delegate::removeListener);
  }

  @Override
  public List<TimeDTO> getAllTimes() {
    return this.delegate.getAllTimes().stream().filter(this::filterTime).toList();
  }

  @Override
  public List<ParticipantDTO> getAllParticipants() {
    return this.delegate.getAllParticipants().stream().filter(this::filterParticipant).toList();
  }

  @Override
  public void updateTime(int raceID, TimeDTO time) {
    this.delegate.updateTime(raceID, time);
  }

  private boolean filterTime(TimeDTO time) {
    return time.getStationId() == this.stationId && time.getStartNbr().equals(this.startNbr);
  }

  private boolean filterParticipant(ParticipantDTO participant) {
    return participant.getStartNbr().equals(this.startNbr);
  }
}
